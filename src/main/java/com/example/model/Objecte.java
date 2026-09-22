package com.example.model;

public class Objecte {

    private final String nom;
    private final String descripcio;
    private final boolean agafable;

    public Objecte(String nom, String descripcio, boolean agafable) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.agafable = agafable;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public boolean isAgafable() {
        return agafable;
    }

    public void usar(Jugador jugador) {
        System.out.println("  No passa res amb " + nom + ".");
    }

    @Override
    public String toString() {
        return nom;
    }
}
