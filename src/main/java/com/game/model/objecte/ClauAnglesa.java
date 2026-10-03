package com.game.model.objecte;

import com.game.model.personatges.Bestia;
import com.game.model.Jugador;
import com.game.model.Objecte;

public class ClauAnglesa extends Objecte {

    public ClauAnglesa() {
        super("ClauAnglesa",
                "Una clau anglesa pesada de ferro. Serveix per reparar la caldera de vapor.",
                true);
    }

    public void repararCaldera() {
        System.out.println("  Repares la caldera de vapor amb la ClauAnglesa.");
    }

    public void usarContraBestia(Bestia bestia, Jugador jugador) {
        System.out.println("  Ataques la Bestia amb la ClauAnglesa. Et clava una xeringa: t'ha enverinat!");
        jugador.enverinar();
    }
}
