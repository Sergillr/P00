package com.example.model;

public class Jugador {

    private final String nom;
    private Zona zonaActual;
    private final Inventari inventari;

    public Jugador(String nom) {
        this.nom = nom;
        this.zonaActual = null;
        this.inventari = new Inventari();
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

    public void mostrarInventari() {
        inventari.mostrar();
    }

    @Override
    public String toString() {
        return nom;
    }
}
