package com.example.model.personatges;

import com.example.model.Jugador;
import com.example.model.Personatge;
import com.example.model.Zona;
import com.example.model.objecte.GaletesDeTe;

public class Bestia extends Personatge {

    private boolean distreta;
    private int comptadorMoviment;

    public Bestia(Zona zonaInicial) {
        super("La Bèstia", zonaInicial);
        this.distreta = false;
        this.comptadorMoviment = 0;
    }

    public void moureAleatoriament() {
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
    }

    @Override
    public String parlar(String frase) {
        return "Grrr...";
    }
}
