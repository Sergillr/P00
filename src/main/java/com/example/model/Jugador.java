package com.example.model;

public class Jugador {

    private final String nom;
    private Zona zonaActual;
    private final Inventari inventari;
    private boolean abricPosat;

    public Jugador(String nom) {
        this.nom = nom;
        this.zonaActual = null;
        this.inventari = new Inventari();
        this.abricPosat = false;
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

    public Inventari getInventari() {
        return inventari;
    }

    public boolean afegirInventari(Objecte objecte) {
        return inventari.afegir(objecte);
    }

    public boolean eliminarInventari(Objecte objecte) {
        return inventari.eliminar(objecte);
    }

    public Objecte obtenirObjecte(String nom) {
        return inventari.obtenir(nom);
    }

    public void agafar(Objecte objecte) {
        if (objecte != null && objecte.isAgafable()) {
            inventari.afegir(objecte);
        }
    }

    public void deixar(Objecte objecte) {
        if (objecte != null) {
            inventari.eliminar(objecte);
        }
    }

    public boolean isAbricPosat() {
        return abricPosat;
    }

    public void posarAbric() {
        abricPosat = true;
    }

    public void treureAbric() {
        abricPosat = false;
    }

    public void mostrarInventari() {
        inventari.mostrar();
    }

    @Override
    public String toString() {
        return nom;
    }
}
