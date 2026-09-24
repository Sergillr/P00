package com.example.core;

import com.example.model.Jugador;
import com.example.model.Mansio;
import com.example.model.Porta;
import com.example.model.Zona;
import com.example.ui.Menu;

public class Joc {

    private Jugador jugador;
    private final Mansio mansio;
    private boolean estat;
    private Zona zonaActual;

    public Joc() {
        this.jugador = new Jugador("Jugador");
        this.mansio = new Mansio();
        this.estat = true;
        this.zonaActual = null;
    }

    public void iniciarPartida() {
        System.out.println();
        System.out.println("  INICIANT PARTIDA...");
        System.out.println();

        mansio.inicialitzar();

        zonaActual = mansio.obtenirZona("Dormitori Principal");
        jugador.setZonaActual(zonaActual);

        System.out.println();
        System.out.println(zonaActual.mostrarDescripcio());
    }

    public void executarPartida(Menu menu) {
        while (estat) {
            System.out.print(" > ");
            String ordre = menu.llegirOrdre();
            if (ordre == null) continue;

            String[] parts = menu.descomposarOrdre(ordre);
            String verb = parts[0];
            String objectiu = parts[1];

            processarOrdre(verb, objectiu, menu);
        }
    }

    private void processarOrdre(String verb, String objectiu, Menu menu) {
        switch (verb) {
            case "sortir" -> {
                System.out.println();
                System.out.println("  Adeu! Gracies per jugar.");
                estat = false;
            }

            case "ajuda" -> menu.mostrarInstruccions();

            case "anar" -> moureDireccio(objectiu);

            case "nord", "sud", "est", "oest", "amunt", "avall" -> moureDireccio(verb);
            case "obrir" -> obrirDireccio(objectiu);
            case "inventari" -> jugador.mostrarInventari();
            default -> {
                System.out.println();
                System.out.println("  No entenc aquesta ordre. Escriu 'ajuda' per veure les comandes disponibles.");
                System.out.println();
            }
        }
    }

    private void moureDireccio(String direccio) {
        if (direccio == null || direccio.isEmpty()) {
            System.out.println();
            System.out.println("  Cap a on vols anar? Escriu: ANAR [nord/sud/est/oest/amunt/avall]");
            System.out.println();
            return;
        }

        if (!direccio.equals("nord") && !direccio.equals("sud") &&
            !direccio.equals("est") && !direccio.equals("oest") &&
            !direccio.equals("amunt") && !direccio.equals("avall")) {
            System.out.println();
            System.out.println("  Direccio no valida. Utilitza: nord, sud, est, oest, amunt o avall.");
            System.out.println();
            return;
        }

        Porta porta = zonaActual.buscarPortaPerDireccio(direccio);

        if (porta == null) {
            System.out.println();
            System.out.println("  No hi ha cap sortida cap al " + direccio + ".");
            System.out.println();
            return;
        }

        if (!porta.isOberta()) {
            System.out.println();
            System.out.println("  La porta cap al " + direccio + " es tancada.");
            if (porta.isRequereixClau()) {
                System.out.println("  Necessites una clau per obrir-la.");
            } else {
                System.out.println("  Prova amb: OBRIR " + direccio);
            }
            System.out.println();
            return;
        }

        Zona desti = porta.obtenirDesti();
        if (desti != null) {
            zonaActual = desti;
            jugador.setZonaActual(desti);
            System.out.println();
            System.out.println(desti.mostrarDescripcio());
        }
    }

    private void obrirDireccio(String direccio) {
        if (direccio == null || direccio.isEmpty()) {
            System.out.println();
            System.out.println("  Quina porta vols obrir? Escriu: OBRIR [nord/sud/est/oest/amunt/avall]");
            System.out.println();
            return;
        }

        Porta porta = zonaActual.buscarPortaPerDireccio(direccio);
        if (porta == null) {
            System.out.println();
            System.out.println("  No hi ha cap porta cap al " + direccio + ".");
            System.out.println();
            return;
        }

        if (porta.isOberta()) {
            System.out.println();
            System.out.println("  La porta cap al " + direccio + " ja es oberta.");
            System.out.println();
            return;
        }

        if (porta.isRequereixClau()) {
            if (jugador.getInventari().conte("ClauDeCoure")) {
                porta.obrirAmbClau();
                System.out.println();
                System.out.println("  Obres la porta cap al " + direccio + " amb la ClauDeCoure.");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("  La porta cap al " + direccio + " necessita la ClauDeCoure (o l'ajut del Majordom).");
                System.out.println();
            }
            return;
        }

        porta.obrir();
        System.out.println();
        System.out.println("  Obres la porta cap al " + direccio + ".");
        System.out.println();
    }

    public boolean comprovarFinal() {
        return !estat;
    }

    public void reiniciarPartida() {
        jugador = new Jugador("Jugador");
        estat = true;
        zonaActual = null;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Mansio getMansio() {
        return mansio;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }
}
