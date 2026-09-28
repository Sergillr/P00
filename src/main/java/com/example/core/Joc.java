package com.example.core;

import com.example.model.personatges.Bestia;
import com.example.model.Jugador;
import com.example.model.Mansio;
import com.example.model.Objecte;
import com.example.model.Porta;
import com.example.model.Zona;
import com.example.model.objecte.ClauDeCoure;
import com.example.model.objecte.GaletesDeTe;
import com.example.ui.Menu;
import java.util.ArrayList;
import java.util.Random;

public class Joc {

    private Jugador jugador;
    private Mansio mansio;
    private boolean estat;
    private Zona zonaActual;
    private Bestia bestia;

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

        this.jugador = new Jugador("Jugador");
        this.mansio = new Mansio();
        this.estat = true;
        this.zonaActual = null;

        mansio.inicialitzar();

        zonaActual = mansio.obtenirZona("Dormitori Principal");
        jugador.setZonaActual(zonaActual);

        ArrayList<Zona> amagatalls = new ArrayList<>();
        for (Zona zona : mansio.getZones()) {
            if (!zona.getNom().equals("Dormitori Principal")) {
                amagatalls.add(zona);
            }
        }
        bestia = new Bestia(amagatalls.get(new Random().nextInt(amagatalls.size())));

        System.out.println();
        System.out.println(zonaActual.mostrarDescripcio());
    }

    public void executarPartida(Menu menu) {
        while (estat) {
            String ordre = menu.llegirOrdre();
            if (ordre == null) continue;

            String[] parts = menu.descomposarOrdre(ordre);
            String verb = parts[0];
            String objectiu = parts[1];
            if (!parts[2].isEmpty()) {
                objectiu = objectiu + " " + parts[2];
            }

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

            case "ajuda", "ayuda" -> menu.mostrarInstruccions();

            case "anar" -> moureDireccio(objectiu);

            case "nord", "sud", "est", "oest", "amunt", "avall" -> moureDireccio(verb);
            case "obrir" -> obrirDireccio(objectiu);
            case "agafar" -> agafarObjecte(objectiu);
            case "deixar" -> deixarObjecte(objectiu);
            case "usar" -> usarObjecte(objectiu);
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
            System.out.println("  La porta que porta cap a " + porta.getZonaDesti().getNom() + " es tancada.");
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
            comprovarBestia();
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
            Objecte obj = jugador.getInventari().obtenir("ClauDeCoure");
            if (obj instanceof ClauDeCoure clau && porta.obrir(clau)) {
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

    private void agafarObjecte(String nom) {
        if (nom == null || nom.isEmpty()) {
            System.out.println();
            System.out.println("  Que vols agafar? Escriu: AGAFAR [objecte]");
            System.out.println();
            return;
        }

        Objecte obj = zonaActual.buscarObjecte(nom);
        if (obj == null) {
            System.out.println();
            System.out.println("  No hi ha cap '" + nom + "' aqui.");
            System.out.println();
            return;
        }

        if (!obj.isAgafable()) {
            System.out.println();
            System.out.println("  No pots agafar " + obj.getNom() + ".");
            System.out.println();
            return;
        }

        zonaActual.eliminarObjecte(obj);
        jugador.agafar(obj);
        System.out.println();
        System.out.println("  Has agafat " + obj.getNom() + ".");
        System.out.println();
    }

    private void deixarObjecte(String nom) {
        if (nom == null || nom.isEmpty()) {
            System.out.println();
            System.out.println("  Que vols deixar? Escriu: DEIXAR [objecte]");
            System.out.println();
            return;
        }

        Objecte obj = jugador.obtenirObjecte(nom);
        if (obj == null) {
            System.out.println();
            System.out.println("  No portes cap '" + nom + "' a sobre.");
            System.out.println();
            return;
        }

        jugador.deixar(obj);
        zonaActual.afegirObjecte(obj);
        System.out.println();
        System.out.println("  Has deixat " + obj.getNom() + ".");
        System.out.println();
    }

    private void usarObjecte(String nom) {
        if (nom == null || nom.isEmpty()) {
            System.out.println();
            System.out.println("  Que vols usar? Escriu: USAR [objecte]");
            System.out.println();
            return;
        }

        Objecte obj = jugador.obtenirObjecte(nom);
        if (obj == null) {
            System.out.println();
            System.out.println("  No portes cap '" + nom + "' a sobre.");
            System.out.println();
            return;
        }

        if (obj instanceof GaletesDeTe galetes) {
            if (bestia != null && zonaActual == bestia.getZonaActual()) {
                if (bestia.isDistreta()) {
                    System.out.println();
                    System.out.println("  La Bèstia ja està distreta amb les galetes.");
                    System.out.println();
                } else {
                    galetes.usar(bestia);
                    bestia.distreure(galetes);
                    jugador.deixar(obj);
                    System.out.println("  La Bèstia es distreu menjant les galetes. Pots passar sense perill... de moment.");
                    System.out.println();
                }
            } else {
                System.out.println();
                System.out.println("  Ofereixes les galetes... però aquí no hi ha ningú.");
                System.out.println();
            }
            return;
        }

        obj.usar(jugador);
    }

    private void comprovarBestia() {
        if (bestia == null || zonaActual != bestia.getZonaActual()) {
            return;
        }
        System.out.println();
        System.out.println("  La Bèstia és aquí, amagada entre les ombres!");
        if (jugador.getInventari().conte("GaletesDeTe")) {
            System.out.println("  Portes GaletesDeTe a sobre. Pots distreure-la oferint-li galetes: USAR GALETESDETE");
        } else {
            System.out.println("  No portes res per distreure-la... Millor no atacar-la directament.");
        }
        System.out.println();
    }

    public boolean comprovarFinal() {
        return !estat;
    }

    public void reiniciarPartida() {
        jugador = new Jugador("Jugador");
        mansio = new Mansio();
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

    public Bestia getBestia() {
        return bestia;
    }
}
