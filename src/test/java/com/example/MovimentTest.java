package com.example;

import com.example.core.Joc;
import com.example.model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovimentTest {

    private Joc joc;
    private Mansio mansio;
    private Jugador jugador;

    @BeforeEach
    public void setUp() {
        joc = new Joc();
        mansio = joc.getMansio();
        jugador = joc.getJugador();
        mansio.inicialitzar();
        joc.iniciarPartida();
    }

    // TESTS DE ZONES

    @Test
    public void testCrearZona() {
        Zona zona = new Zona("Sala", "Una sala gran");
        assertEquals("Sala", zona.getNom());
        assertEquals("Una sala gran", zona.getDescripcio());
        assertFalse(zona.isFosca());
        assertTrue(zona.getObjectes().isEmpty());
        assertTrue(zona.getSortides().isEmpty());
    }

    @Test
    public void testCrearZonaFosca() {
        Zona zona = new Zona("Celler", "Un lloc fosc", true);
        assertTrue(zona.isFosca());
    }

    @Test
    public void testAfegirObjecteAZona() {
        Zona zona = new Zona("Sala", "Una sala gran");
        Objecte obj = new Objecte("Taula", "Una taula de fusta", false);
        zona.afegirObjecte(obj);
        assertEquals(1, zona.getObjectes().size());
        assertEquals("Taula", zona.getObjectes().get(0).getNom());
    }

    @Test
    public void testEliminarObjecteDeZona() {
        Zona zona = new Zona("Sala", "Una sala gran");
        Objecte obj = new Objecte("Taula", "Una taula de fusta", false);
        zona.afegirObjecte(obj);
        zona.eliminarObjecte(obj);
        assertTrue(zona.getObjectes().isEmpty());
    }

    @Test
    public void testBuscarObjecteAZona() {
        Zona zona = new Zona("Sala", "Una sala gran");
        Objecte obj = new Objecte("Taula", "Una taula de fusta", false);
        zona.afegirObjecte(obj);
        assertEquals(obj, zona.buscarObjecte("taula"));
        assertNull(zona.buscarObjecte("cadi"));
    }

    @Test
    public void testBuscarObjecteSenseResultats() {
        Zona zona = new Zona("Sala", "Una sala gran");
        assertNull(zona.buscarObjecte("res"));
    }

    @Test
    public void testMostrarDescripcioZona() {
        Zona zona = new Zona("Sala", "Una sala gran");
        String desc = zona.mostrarDescripcio();
        assertTrue(desc.contains("Sala"));
        assertTrue(desc.contains("Una sala gran"));
    }

    @Test
    public void testMostrarDescripcioZonaFosca() {
        Zona zona = new Zona("Celler", "Un lloc fosc", true);
        String desc = zona.mostrarDescripcio();
        assertTrue(desc.contains("fosc"));
    }

    @Test
    public void testMostrarDescripcioAmbObjectes() {
        Zona zona = new Zona("Sala", "Una sala gran");
        Objecte obj = new Objecte("Taula", "Una taula de fusta", false);
        zona.afegirObjecte(obj);
        String desc = zona.mostrarDescripcio();
        assertTrue(desc.contains("Taula"));
    }

    // TESTS DE PORTES

    @Test
    public void testCrearPorta() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti);

        assertEquals("sud", porta.getDireccio());
        assertEquals(origen, porta.getZonaOrigen());
        assertEquals(desti, porta.getZonaDesti());
        assertTrue(porta.isOberta());
        assertFalse(porta.isRequereixClau());
    }

    @Test
    public void testCrearPortaTancada() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Celler", "Un celler");
        Porta porta = new Porta("sud", origen, desti, false);

        assertFalse(porta.isOberta());
    }

    @Test
    public void testObrirPorta() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti, false);

        assertTrue(porta.obrir());
        assertTrue(porta.isOberta());
    }

    @Test
    public void testObrirPortaAmbClau() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Celler", "Un celler");
        Porta porta = new Porta("sud", origen, desti, false, true);

        assertTrue(porta.obrirAmbClau());
        assertTrue(porta.isOberta());
    }

    @Test
    public void testObrirPortaSenseClau() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Celler", "Un celler");
        Porta porta = new Porta("sud", origen, desti, false, true);

        assertFalse(porta.obrir());
        assertFalse(porta.isOberta());
    }

    @Test
    public void testTancarPorta() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti);

        assertTrue(porta.isOberta());
        porta.tancar();
        assertFalse(porta.isOberta());
    }

    @Test
    public void testObtenirDestiPortaOberta() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti);

        assertEquals(desti, porta.obtenirDesti());
    }

    @Test
    public void testObtenirDestiPortaTancada() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti, false);

        assertNull(porta.obtenirDesti());
    }

    @Test
    public void testBuscarPortaPerDireccio() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona destiNord = new Zona("Nord", "Al nord");
        Zona destiSud = new Zona("Sud", "Al sud");
        Porta portaNord = new Porta("nord", origen, destiNord);
        Porta portaSud = new Porta("sud", origen, destiSud);
        origen.afegirSortida(portaNord);
        origen.afegirSortida(portaSud);

        assertEquals(portaNord, origen.buscarPortaPerDireccio("nord"));
        assertEquals(portaSud, origen.buscarPortaPerDireccio("sud"));
        assertNull(origen.buscarPortaPerDireccio("est"));
    }

    // TESTS DE MANSIO

    @Test
    public void testAfegirZonaAMansio() {
        Mansio m = new Mansio();
        Zona zona = new Zona("Sala", "Una sala gran");
        m.afegirZona(zona);
        assertEquals(1, m.getZones().size());
    }

    @Test
    public void testAfegirPortaAMansio() {
        Mansio m = new Mansio();
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Cuina", "Una cuina");
        Porta porta = new Porta("sud", origen, desti);
        m.afegirPorta(porta);
        assertEquals(1, m.getPortes().size());
    }

    @Test
    public void testObtenirZona() {
        Mansio m = new Mansio();
        Zona zona = new Zona("Sala", "Una sala gran");
        m.afegirZona(zona);
        assertEquals(zona, m.obtenirZona("Sala"));
        assertNull(m.obtenirZona("Bany"));
    }

    @Test
    public void testInicialitzarMansio() {
        Mansio m = new Mansio();
        m.inicialitzar();
        assertNotNull(m.obtenirZona("Dormitori Principal"));
        assertNotNull(m.obtenirZona("Despatx del Senyor"));
    }

    @Test
    public void testInicialitzarDuesZones() {
        Mansio m = new Mansio();
        m.inicialitzar();
        assertEquals(2, m.getZones().size());
        assertEquals(2, m.getPortes().size());
    }

    // TESTS DE MOVIMENT

    @Test
    public void testJugadorComençaAlDormitori() {
        assertEquals("Dormitori Principal", joc.getZonaActual().getNom());
    }

    @Test
    public void testBuscarPortaAlSud() {
        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        Porta porta = dormitori.buscarPortaPerDireccio("sud");
        assertNotNull(porta);
        assertEquals("sud", porta.getDireccio());
        assertEquals("Despatx del Senyor", porta.getZonaDesti().getNom());
    }

    @Test
    public void testBuscarPortaAlNord() {
        Zona despatx = mansio.obtenirZona("Despatx del Senyor");
        Porta porta = despatx.buscarPortaPerDireccio("nord");
        assertNotNull(porta);
        assertEquals("nord", porta.getDireccio());
        assertEquals("Dormitori Principal", porta.getZonaDesti().getNom());
    }

    @Test
    public void testBuscarPortaSenseResultats() {
        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        assertNull(dormitori.buscarPortaPerDireccio("est"));
        assertNull(dormitori.buscarPortaPerDireccio("oest"));
    }

    @Test
    public void testDormitoriTéUnaSortida() {
        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        assertEquals(1, dormitori.getSortides().size());
        assertEquals("sud", dormitori.getSortides().get(0).getDireccio());
    }

    @Test
    public void testDespatxTéUnaSortida() {
        Zona despatx = mansio.obtenirZona("Despatx del Senyor");
        assertEquals(1, despatx.getSortides().size());
        assertEquals("nord", despatx.getSortides().get(0).getDireccio());
    }

    // TESTS DE JUGADOR

    @Test
    public void testJugadorTéInventari() {
        assertNotNull(jugador.getInventari());
    }

    @Test
    public void testJugadorAgafarObjecte() {
        Zona zona = mansio.obtenirZona("Dormitori Principal");
        Objecte obj = new Objecte("Llanterna", "Una llanterna vella", true);
        zona.afegirObjecte(obj);

        assertTrue(jugador.afegirInventari(obj));
        assertEquals(1, jugador.getInventari().getObjectes().size());
    }

    @Test
    public void testJugadorDeixarObjecte() {
        Zona zona = mansio.obtenirZona("Dormitori Principal");
        Objecte obj = new Objecte("Llanterna", "Una llanterna vella", true);
        zona.afegirObjecte(obj);
        jugador.afegirInventari(obj);

        assertTrue(jugador.eliminarInventari(obj));
        assertEquals(0, jugador.getInventari().getObjectes().size());
        assertEquals(1, zona.getObjectes().size());
    }

    // TESTS D'INTEGRACIO

    @Test
    public void testMovimentComplet() {
        assertEquals("Dormitori Principal", joc.getZonaActual().getNom());

        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        Porta porta = dormitori.buscarPortaPerDireccio("sud");
        assertNotNull(porta);
        assertTrue(porta.isOberta());

        Zona desti = porta.obtenirDesti();
        assertNotNull(desti);
        assertEquals("Despatx del Senyor", desti.getNom());
    }

    @Test
    public void testPortaAmbClau() {
        Zona origen = new Zona("Sala", "Una sala");
        Zona desti = new Zona("Celler", "Un celler");
        Porta porta = new Porta("sud", origen, desti, false, true);

        assertTrue(porta.isRequereixClau());
        assertFalse(porta.obrir());

        assertTrue(porta.obrirAmbClau());
        assertTrue(porta.isOberta());
    }

    @Test
    public void testTancarPortaDespresDobrir() {
        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        Porta porta = dormitori.buscarPortaPerDireccio("sud");

        assertTrue(porta.isOberta());
        porta.tancar();
        assertFalse(porta.isOberta());
    }

    @Test
    public void testInventariDespresDagafar() {
        Zona zona = mansio.obtenirZona("Dormitori Principal");
        Objecte obj = new Objecte("Clau", "Una clau vella", true);
        zona.afegirObjecte(obj);

        assertTrue(zona.getObjectes().contains(obj));
        assertTrue(jugador.afegirInventari(obj));
        zona.eliminarObjecte(obj);

        assertFalse(zona.getObjectes().contains(obj));
        assertTrue(jugador.getInventari().getObjectes().contains(obj));
    }

    @Test
    public void testZonaMostraDireccions() {
        Zona dormitori = mansio.obtenirZona("Dormitori Principal");
        String desc = dormitori.mostrarDescripcio();
        assertTrue(desc.contains("sud"));
    }
}
