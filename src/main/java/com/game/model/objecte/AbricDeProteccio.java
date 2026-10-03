package com.game.model.objecte;

import com.game.model.Jugador;
import com.game.model.Objecte;

public class AbricDeProteccio extends Objecte {

    public AbricDeProteccio() {
        super("AbricDeProteccio",
                "Un abric gruixut d'amiant. Protegeix contra el vapor roent de la caldera.",
                true);
    }

    public void posar(Jugador jugador) {
        jugador.posarAbric();
        System.out.println("  Et poses l'AbricDeProteccio. El vapor roent ja no et crema.");
    }

    public void treure(Jugador jugador) {
        jugador.treureAbric();
        System.out.println("  Et treus l'AbricDeProteccio.");
    }
}
