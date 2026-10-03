package com.example.core;

import com.example.model.personatges.Bestia;
import com.example.model.personatges.Majordom;
import com.example.model.personatges.ServentGonzalo;
import com.example.model.Jugador;
import com.example.model.Mansio;
import com.example.model.Objecte;
import com.example.model.Porta;
import com.example.model.Zona;
import com.example.model.objecte.AbricDeProteccio;
import com.example.model.objecte.ClauAnglesa;
import com.example.model.objecte.ClauDeCoure;
import com.example.model.objecte.GaletesDeTe;
import com.example.model.objecte.LlanternaDeQuerosè;
import com.example.ui.Menu;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Joc {

    private static final String HISTORIA = """
        Hivern de 1885.

        El senyor de la mansió s'ha despertat per un soroll ensordidor.
        El seu majordom obre la porta amb una carta:

        "La Caldera de Vapor del celler ha patit una avaria crítica
        i amenaça amb fer saltar pels aires tota la casa.
        Intenteu de salvar la vostra pell y la del reste de habitants
        de la mansio. I tingueu compta amb la bestia.

        PD: No tracteu de fugir, me he assegurat de que no pugueu sortir.

        Atentament,
        -La bestia\"""".replaceAll("(?m)^(?=\\S)", "  ");

    private Jugador jugador;
    private Mansio mansio;
    private boolean estat;
    private Zona zonaActual;
    private Zona zonaAnterior;
    private Bestia bestia;
    private Majordom majordom;
    private ServentGonzalo gonzalo;
    private final Interpretador interpretador;
    private final Menu menu;
    private int tornsCaldera;
    private boolean calderaReparada;

    public Joc() {
        this(new Menu());
    }

    public Joc(Menu menu) {
        this.jugador = new Jugador("Jugador");
        this.mansio = new Mansio();
        this.estat = true;
        this.zonaActual = null;
        this.interpretador = new Interpretador();
        this.menu = menu;
    }

    public void iniciarPartida() {
        mostrar("INICIANT PARTIDA...");
        System.out.println(HISTORIA);
        System.out.println();
        System.out.println("  Tens 20 torns abans que la caldera exploti. Afanya't!");

        this.jugador = new Jugador("Jugador");
        this.mansio = new Mansio();
        this.estat = true;
        this.zonaActual = null;
        this.zonaAnterior = null;
        this.tornsCaldera = 20;
        this.calderaReparada = false;

        mansio.inicialitzar();

        zonaActual = mansio.obtenirZona("Dormitori Principal");
        jugador.setZonaActual(zonaActual);

        bestia = new Bestia(zonaMesAllunyada(mansio.obtenirZona("Dormitori Principal")));
        majordom = new Majordom(mansio.obtenirZona("Despatx del Senyor"), mansio);
        gonzalo = new ServentGonzalo(mansio.obtenirZona("Cuina"));
        mansio.afegirPersonatge(bestia);
        mansio.afegirPersonatge(majordom);
        mansio.afegirPersonatge(gonzalo);

        System.out.println();
        System.out.println();
        System.out.println(zonaActual.mostrarDescripcio(!estaAFosques()));
    }

    private Zona zonaMesAllunyada(Zona origen) {
        HashMap<Zona, Integer> distancia = new HashMap<>();
        ArrayDeque<Zona> cua = new ArrayDeque<>();
        distancia.put(origen, 0);
        cua.add(origen);
        Zona llunyana = origen;
        while (!cua.isEmpty()) {
            Zona actual = cua.poll();
            for (Porta porta : actual.getSortides()) {
                Zona seguent = porta.getZonaDesti();
                if (!distancia.containsKey(seguent)) {
                    distancia.put(seguent, distancia.get(actual) + 1);
                    cua.add(seguent);
                    if (distancia.get(seguent) > distancia.get(llunyana)) {
                        llunyana = seguent;
                    }
                }
            }
        }
        return llunyana;
    }

    public void executarPartida() {
        while (estat) {
            String ordre = menu.llegirOrdre();
            if (ordre == null) continue;

            processarOrdre(ordre);
        }
    }

    public void processarOrdre(String ordre) {
        if (ordre == null || ordre.isBlank()) {
            return;
        }
        String neta = ordre.trim().toLowerCase();
        switch (neta) {
            case "sortir" -> {
                System.out.println();
                System.out.println("  Adeu! Gracies per jugar.");
                estat = false;
                return;
            }
            case "ajuda", "ayuda" -> mostrar(Menu.obtenirInstruccions());
            case "inventari" -> jugador.mostrarInventari();
            default -> {
                Comanda comanda = interpretador.interpretar(neta);
                if (comanda == null) {
                    mostrar("No entenc aquesta ordre. Escriu 'ajuda' per veure les comandes disponibles.");
                } else {
                    comanda.executar(this);
                    if (estat) {
                        passarTorn();
                    }
                }
            }
        }
    }

    public void moure(String direccio) {
        if (direccio == null || direccio.isEmpty()) {
            mostrar("Cap a on vols anar? Escriu: ANAR [nord/sud/est/oest/amunt/avall]");
            return;
        }

        if (!direccio.equals("nord") && !direccio.equals("sud") &&
            !direccio.equals("est") && !direccio.equals("oest") &&
            !direccio.equals("amunt") && !direccio.equals("avall")) {
            mostrar("Direccio no valida. Utilitza: nord, sud, est, oest, amunt o avall.");
            return;
        }

        Porta porta = zonaActual.buscarPortaPerDireccio(direccio);

        if (porta == null) {
            mostrar("No hi ha cap sortida cap al " + direccio + ".");
            return;
        }

        if (estaAFosques() && (zonaAnterior == null || porta.getZonaDesti() != zonaAnterior)) {
            mostrar("És massa fosc, no veus on vas. Pots tornar enrere o encendre una llum (ENCENDRE LLANTERNA).");
            return;
        }

        if (!porta.isOberta()) {
            if (porta.isRequereixClau()) {
                mostrar("La porta que porta cap a " + porta.getZonaDesti().getNom() + " es tancada.",
                        "Necessites una clau per obrir-la.");
            } else {
                mostrar("La porta que porta cap a " + porta.getZonaDesti().getNom() + " es tancada.",
                        "Prova amb: OBRIR " + direccio);
            }
            return;
        }

        Zona desti = porta.obtenirDesti();
        if (desti != null) {
            zonaAnterior = zonaActual;
            zonaActual = desti;
            jugador.moure(desti);
            System.out.println();
            System.out.println();
            System.out.println(desti.mostrarDescripcio(!estaAFosques()));
            if (desti.getNom().equals("Celler de la Caldera") && !jugador.isAbricPosat()) {
                mostrar("El vapor roent de la caldera et crema viu. Sense l'AbricDeProteccio no tenies cap oportunitat. Has perdut.");
                estat = false;
                return;
            }
            if (desti.getNom().equals("Despatx del Senyor") && calderaReparada) {
                mostrar("Sobre l'escriptori trobes una carta amb una lletra estranya i tremolosa:",
                        "\"M'he divertit molt amb aquest experiment i n'he après molt.\"",
                        "\"Però ara me n'he d'anar. La caldera ja és estable i queda en les vostres mans.\"",
                        "\"Fins a la propera tempesta.\"",
                        "-La bestia");
                mostrar("HAS ESTABILITZAT EL SISTEMA. HAS GUANYAT!");
                estat = false;
                return;
            }
            if (!estaAFosques()) {
                comprovarBestia();
                comprovarGonzalo();
            }
        }
    }

    public void obrir(String direccio) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (direccio == null || direccio.isEmpty()) {
            mostrar("Quina porta vols obrir? Escriu: OBRIR [nord/sud/est/oest/amunt/avall]");
            return;
        }

        Porta porta = zonaActual.buscarPortaPerDireccio(direccio);
        if (porta == null) {
            mostrar("No hi ha cap porta cap al " + direccio + ".");
            return;
        }

        if (porta.isOberta()) {
            mostrar("La porta cap al " + direccio + " ja es oberta.");
            return;
        }

        if (porta.isRequereixClau()) {
            Objecte obj = jugador.getInventari().obtenir("ClauDeCoure");
            if (obj instanceof ClauDeCoure clau && porta.obrir(clau)) {
                mostrar("Obres la porta cap al " + direccio + " amb la ClauDeCoure.");
            } else if (majordom != null && zonaActual == majordom.getZonaActual()
                    && majordom.obrirPorta(porta)) {
                mostrar("El Majordom t'obre la porta cap al " + direccio + ".");
            } else {
                mostrar("La porta cap al " + direccio + " necessita la ClauDeCoure (o l'ajut del Majordom).");
            }
            return;
        }

        porta.obrir();
        mostrar("Obres la porta cap al " + direccio + ".");
    }

    public void tancar(String direccio) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (direccio == null || direccio.isEmpty()) {
            mostrar("Quina porta vols tancar? Escriu: TANCAR [nord/sud/est/oest/amunt/avall]");
            return;
        }

        Porta porta = zonaActual.buscarPortaPerDireccio(direccio);
        if (porta == null) {
            mostrar("No hi ha cap porta cap al " + direccio + ".");
            return;
        }

        if (!porta.isOberta()) {
            mostrar("La porta cap al " + direccio + " ja es tancada.");
            return;
        }

        porta.tancar();
        mostrar("Tanques la porta cap al " + direccio + ".");
    }

    private static void mostrar(String... linies) {
        System.out.println();
        for (String linia : linies) {
            System.out.println("  " + linia);
        }
        System.out.println();
    }

    private static Objecte buscarPerNom(List<Objecte> objectes, String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        for (Objecte obj : objectes) {
            if (obj.getNom().equalsIgnoreCase(nom)) {
                return obj;
            }
        }
        String clau = nom.toLowerCase();
        for (Objecte obj : objectes) {
            if (obj.getNom().toLowerCase().contains(clau)) {
                return obj;
            }
        }
        return null;
    }

    private Objecte buscarZona(String nom) {
        return buscarPerNom(zonaActual.getObjectes(), nom);
    }

    public void agafar(String nom) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (nom == null || nom.isEmpty()) {
            mostrar("Que vols agafar? Escriu: AGAFAR [objecte]");
            return;
        }

        Objecte obj = buscarZona(nom);
        if (obj == null) {
            mostrar("No hi ha cap '" + nom + "' aqui.");
            return;
        }

        if (!obj.isAgafable()) {
            mostrar("No pots agafar " + obj.getNom() + ".");
            return;
        }

        zonaActual.eliminarObjecte(obj);
        jugador.agafar(obj);
        mostrar("Has agafat " + obj.getNom() + ".");
    }

    public void deixar(String nom) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (nom == null || nom.isEmpty()) {
            mostrar("Que vols deixar? Escriu: DEIXAR [objecte]");
            return;
        }

        Objecte obj = buscarInventari(nom);
        if (obj == null) {
            mostrar("No portes cap '" + nom + "' a sobre.");
            return;
        }

        jugador.deixar(obj);
        zonaActual.afegirObjecte(obj);
        mostrar("Has deixat " + obj.getNom() + ".");
    }

    public void usar(String nom) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (nom == null || nom.isEmpty()) {
            mostrar("Que vols usar? Escriu: USAR [objecte]");
            return;
        }

        Objecte obj = buscarInventari(nom);
        if (obj == null) {
            mostrar("No portes cap '" + nom + "' a sobre.");
            return;
        }

        if (obj instanceof GaletesDeTe galetes) {
            boolean bestiaAqui = bestia != null && zonaActual == bestia.getZonaActual();
            boolean gonzaloAqui = gonzalo != null && zonaActual == gonzalo.getZonaActual();
            if (bestiaAqui && !bestia.isDistreta()) {
                galetes.usar(bestia);
                bestia.distreure(galetes);
                jugador.deixar(obj);
                System.out.println("  La Bèstia es distreu menjant les galetes. Pots passar sense perill... de moment.");
                System.out.println();
            } else if (gonzaloAqui && gonzalo.isDespert()) {
                gonzalo.menjarGaletes(galetes);
                jugador.deixar(obj);
                mostrar("En Gonzalo es menja les galetes, entretingut. De moment no rondarà.");
            } else if (bestiaAqui) {
                mostrar("La Bèstia ja està distreta amb les galetes.");
            } else if (gonzaloAqui) {
                mostrar("En Gonzalo dorm plàcidament. No et fa cas.");
            } else {
                mostrar("Ofereixes les galetes... però aquí no hi ha ningú.");
            }
            return;
        }

        if (obj instanceof AbricDeProteccio abric) {
            if (jugador.isAbricPosat()) {
                abric.treure(jugador);
                if (zonaActual.getNom().equals("Celler de la Caldera")) {
                    mostrar("Et treus l'abric en ple vapor roent. Et crema viu. Has perdut.");
                    estat = false;
                }
            } else {
                abric.posar(jugador);
            }
            return;
        }

        if (obj instanceof ClauAnglesa clau) {
            if (bestia != null && zonaActual == bestia.getZonaActual() && !bestia.isDistreta()) {
                clau.usarContraBestia(bestia, jugador);
                return;
            }
            if (zonaActual.getNom().equals("Celler de la Caldera")) {
                if (calderaReparada) {
                    mostrar("La caldera ja està reparada. Només cal tornar al Despatx a estabilitzar el sistema.");
                } else if (!jugador.isAbricPosat()) {
                    mostrar("El vapor roent et impedeix treballar. Posa't l'AbricDeProteccio primer: USAR ABRIC");
                } else {
                    clau.repararCaldera();
                    calderaReparada = true;
                    mostrar("La caldera deixa de xiular. L'has reparada! Ara torna al Despatx del Senyor a estabilitzar el sistema.");
                }
                return;
            }
        }

        jugador.usar(obj);
    }

    private boolean estaAFosques() {
        if (zonaActual == null || !zonaActual.isFosca()) {
            return false;
        }
        Objecte obj = jugador.obtenirObjecte("LlanternaDeQuerosè");
        return !(obj instanceof LlanternaDeQuerosè ll && ll.isEncesa());
    }

    private boolean bloquejatPerFoscor() {
        if (!estaAFosques()) {
            return false;
        }
        mostrar("És massa fosc, no veus res. Pots tornar enrere o encendre una llum (ENCENDRE LLANTERNA).");
        return true;
    }

    private Objecte buscarInventari(String nom) {
        return buscarPerNom(jugador.getInventari().getObjectes(), nom);
    }

    public void encendre(String nom) {
        if (nom == null || nom.isEmpty()) {
            mostrar("Què vols encendre? Escriu: ENCENDRE [objecte]");
            return;
        }

        Objecte obj = buscarInventari(nom);
        if (obj == null) {
            mostrar("No portes cap '" + nom + "' a sobre.");
            return;
        }

        if (obj instanceof LlanternaDeQuerosè ll) {
            if (ll.isEncesa()) {
                mostrar("La llanterna ja està encesa.");
            } else {
                ll.encendre();
            }
        } else {
            mostrar("No pots encendre " + obj.getNom() + ".");
        }
    }

    public void apagar(String nom) {
        if (nom == null || nom.isEmpty()) {
            mostrar("Què vols apagar? Escriu: APAGAR [objecte]");
            return;
        }

        Objecte obj = buscarInventari(nom);
        if (obj == null) {
            mostrar("No portes cap '" + nom + "' a sobre.");
            return;
        }

        if (obj instanceof LlanternaDeQuerosè ll) {
            if (!ll.isEncesa()) {
                mostrar("La llanterna ja està apagada.");
            } else {
                ll.apagar();
            }
        } else {
            mostrar("No pots apagar " + obj.getNom() + ".");
        }
    }

    public void parlar(String objectiu) {
        if (bloquejatPerFoscor()) {
            return;
        }
        if (majordom != null && zonaActual == majordom.getZonaActual()) {
            String text = objectiu == null ? "" : objectiu.toLowerCase();
            String resposta;
            if (text.contains("bestia") || text.contains("bèstia")) {
                resposta = majordom.dirUbicacioBestia(bestia);
            } else {
                resposta = majordom.parlar(objectiu);
            }
            mostrar(resposta);
            return;
        }

        if (gonzalo != null && zonaActual == gonzalo.getZonaActual()) {
            mostrar("En Gonzalo diu: \"" + gonzalo.parlar(objectiu) + "\"");
            return;
        }

        if (bestia != null && zonaActual == bestia.getZonaActual()) {
            mostrar("La Bèstia no parla, només grunyeix: \"" + bestia.parlar(objectiu) + "\"");
            return;
        }

        mostrar("Aquí no hi ha ningú amb qui parlar.");
    }

    private void comprovarBestia() {
        if (bestia == null || zonaActual != bestia.getZonaActual()) {
            return;
        }
        if (bestia.isDistreta()) {
            mostrar("La Bèstia continua distreta amb les galetes.");
            return;
        }
        System.out.println();
        System.out.println("  La Bèstia és aquí, amagada entre les ombres!");
        bestia.atacar(jugador);
        if (jugador.getInventari().conte("GaletesDeTe")) {
            System.out.println("  Portes GaletesDeTe a sobre. Pots distreure-la oferint-li galetes: USAR GALETESDETE");
        } else {
            System.out.println("  No portes res per distreure-la... Millor no atacar-la directament.");
        }
        System.out.println();
    }

    private void comprovarGonzalo() {
        if (gonzalo == null || zonaActual != gonzalo.getZonaActual()) {
            return;
        }
        if (!gonzalo.isDespert()) {
            gonzalo.despertar();
            mostrar("En Gonzalo es desperta sobresaltat! A partir d'ara voltarà per la mansió buscant dolços.");
        } else {
            mostrar("En Gonzalo és aquí, despistat.");
        }
    }

    private void passarTorn() {
        tornGonzalo();
        tornBestia();
        jugador.decrementarEnverinament();
        if (jugador.haMortEnverinat()) {
            mostrar("El verí t'ha matat. Has perdut.");
            estat = false;
            return;
        }
        if (calderaReparada) {
            return;
        }
        tornsCaldera--;
        if (tornsCaldera <= 0) {
            mostrar("La Caldera de Vapor ha explotat i s'ha emportat la mansió pels aires. Has perdut.");
            estat = false;
        } else if (tornsCaldera == 10) {
            mostrar("La caldera xiula cada cop més fort... queden 10 torns!");
        } else if (tornsCaldera == 5) {
            mostrar("Les juntes de la caldera cedeixen... queden 5 torns!");
        } else if (tornsCaldera <= 3) {
            mostrar("La caldera està a punt d'explotar... queden " + tornsCaldera + " torns!");
        }
    }

    private void tornGonzalo() {
        if (gonzalo == null || !gonzalo.isDespert()) {
            return;
        }
        Zona zg = gonzalo.getZonaActual();
        if (zg == null) {
            return;
        }
        GaletesDeTe dolc = null;
        for (Objecte o : zg.getObjectes()) {
            if (o instanceof GaletesDeTe g) {
                dolc = g;
                break;
            }
        }
        if (dolc != null) {
            zg.eliminarObjecte(dolc);
            gonzalo.menjarGaletes(dolc);
            if (zg == zonaActual && !estaAFosques()) {
                mostrar("En Gonzalo es menja les GaletesDeTe que troba. Es queda entretingut.");
            }
            return;
        }
        if (gonzalo.isEntretingut()) {
            gonzalo.reposar();
            return;
        }
        Zona abans = zg;
        gonzalo.moureAleatoriament();
        if (gonzalo.getZonaActual() != abans && gonzalo.getZonaActual() == zonaActual && !estaAFosques()) {
            mostrar("En Gonzalo entra voltant, despistat.");
        }
    }

    private void tornBestia() {
        if (bestia == null || bestia.isDistreta()) {
            return;
        }
        bestia.moureAleatoriament();
        if (bestia.getZonaActual() == zonaActual && !estaAFosques()) {
            bestia.atacar(jugador);
        }
    }

    public boolean comprovarFinal() {
        return !estat;
    }

    public void reiniciarPartida() {
        jugador = new Jugador("Jugador");
        mansio = new Mansio();
        estat = true;
        zonaActual = null;
        zonaAnterior = null;
        tornsCaldera = 20;
        calderaReparada = false;
        bestia = null;
        majordom = null;
        gonzalo = null;
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

    public int getTornsCaldera() {
        return tornsCaldera;
    }

    public boolean isCalderaReparada() {
        return calderaReparada;
    }

    public Bestia getBestia() {
        return bestia;
    }

    public Majordom getMajordom() {
        return majordom;
    }

    public ServentGonzalo getGonzalo() {
        return gonzalo;
    }
}
