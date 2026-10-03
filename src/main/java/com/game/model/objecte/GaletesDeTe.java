package com.game.model.objecte;

import com.game.model.Objecte;
import com.game.model.Personatge;

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
