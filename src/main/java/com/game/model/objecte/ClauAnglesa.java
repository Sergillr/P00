package com.example.model.objecte;

import com.example.model.personatges.Bestia;
import com.example.model.Jugador;
import com.example.model.Objecte;

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
