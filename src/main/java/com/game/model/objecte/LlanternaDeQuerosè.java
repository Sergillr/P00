package com.example.model.objecte;

import com.example.model.Objecte;

public class LlanternaDeQuerosè extends Objecte {

    private boolean encesa;

    public LlanternaDeQuerosè() {
        super("LlanternaDeQuerosè",
                "Una llanterna de querosè. Permet il·luminar sales fosques.",
                true);
        this.encesa = false;
    }

    public boolean isEncesa() {
        return encesa;
    }

    public void encendre() {
        encesa = true;
        System.out.println("  Encens la llanterna de querosè.");
    }

    public void apagar() {
        encesa = false;
        System.out.println("  Apagues la llanterna de querosè.");
    }
}
