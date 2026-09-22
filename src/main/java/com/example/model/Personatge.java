package com.example.model;

public abstract class Personatge {

    private final String nom;
    private Zona zonaActual;

    public Personatge(String nom, Zona zonaInicial) {
        this.nom = nom;
        this.zonaActual = zonaInicial;
    }

    public String getNom() {
        return nom;
    }

    public Zona getZonaActual() {
        return zonaActual;
    }

    public void setZonaActual(Zona zona) {
        this.zonaActual = zona;
    }

    public abstract String parlar(String frase);

    @Override
    public String toString() {
        return nom;
    }
}
