package com.game.model.personatges;

import com.game.model.Jugador;
import com.game.model.Personatge;
import com.game.model.Porta;
import com.game.model.Zona;
import com.game.model.objecte.GaletesDeTe;
import java.util.ArrayList;
import java.util.Random;

public class Bestia extends Personatge {

    private static final Random ALEATORI = new Random();
    private boolean distreta;
    private int comptadorMoviment;

    public Bestia(Zona zonaInicial) {
        super("La Bèstia", zonaInicial);
        this.distreta = false;
        this.comptadorMoviment = 0;
    }

    public void moureAleatoriament() {
        Zona actual = getZonaActual();
        if (actual == null) {
            return;
        }
        ArrayList<Porta> obertes = new ArrayList<>();
        for (Porta porta : actual.getSortides()) {
            if (porta.isOberta()) {
                obertes.add(porta);
            }
        }
        if (obertes.isEmpty()) {
            return;
        }
        setZonaActual(obertes.get(ALEATORI.nextInt(obertes.size())).getZonaDesti());
        comptadorMoviment++;
    }

    public void distreure(GaletesDeTe galetes) {
        distreta = true;
    }

    public boolean isDistreta() {
        return distreta;
    }

    public void atacar(Jugador jugador) {
        System.out.println("  La Bèstia t'ataca amb una xeringa!");
        jugador.enverinar();
    }

    @Override
    public String parlar(String frase) {
        return "Grrr...";
    }
}
