package com.example.model.objecte;

import com.example.model.Objecte;
import com.example.model.Personatge;

public class GaletesDeTe extends Objecte {

    public GaletesDeTe() {
        super("GaletesDeTe",
                "Unes galetes de te de rebosteria. Serveixen per entretenir o distreure.",
                true);
    }

    public void usar(Personatge personatge) {
        System.out.println("  Ofereixes les GaletesDeTe a " + personatge.getNom() + ".");
    }
}
