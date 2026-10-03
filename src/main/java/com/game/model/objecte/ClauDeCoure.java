package com.game.model.objecte;

import com.game.model.Objecte;

public class ClauDeCoure extends Objecte {

    private final boolean esLaDelSenyor;

    public ClauDeCoure() {
        super("ClauDeCoure",
                "Una clau de coure massissa. Obre la porta de ferro del Saló que baixa a l'Escala del Celler.",
                true);
        this.esLaDelSenyor = true;
    }

    public ClauDeCoure(boolean esLaDelSenyor) {
        super("ClauDeCoure",
                "Una clau de coure massissa. Obre la porta de ferro del Saló que baixa a l'Escala del Celler.",
                true);
        this.esLaDelSenyor = esLaDelSenyor;
    }

    public boolean isEsLaDelSenyor() {
        return esLaDelSenyor;
    }
}
