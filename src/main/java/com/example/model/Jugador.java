package com.example.model;

public class Jugador {

    private final String nom;
    private Zona zonaActual;
    private final Inventari inventari;
    private boolean abricPosat;
    private int moviments;
    private boolean enverinat;
    private int comptadorEnverinament;

    public Jugador(String nom) {
        this.nom = nom;
        this.zonaActual = null;
        this.inventari = new Inventari();
        this.abricPosat = false;
        this.moviments = 0;
        this.enverinat = false;
        this.comptadorEnverinament = 0;
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

    public void moure(Zona zona) {
        this.zonaActual = zona;
        this.moviments++;
    }

    public int getMoviments() {
        return moviments;
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

    public void usar(Objecte objecte) {
        if (objecte != null) {
            objecte.usar(this);
        }
    }

    public boolean isEnverinat() {
        return enverinat;
    }

    public void enverinar() {
        enverinat = true;
        comptadorEnverinament = 3;
        System.out.println("  T'ha injectat una estranya substància: estàs enverinat!");
    }

    public void decrementarEnverinament() {
        if (!enverinat) {
            return;
        }
        comptadorEnverinament--;
        if (comptadorEnverinament <= 0) {
            enverinat = false;
            comptadorEnverinament = 0;
            System.out.println();
            System.out.println("  L'efecte de la substància passa. Ja no estàs enverinat.");
            System.out.println();
        }
    }

    public void mostrarInventari() {
        inventari.mostrar();
    }

    @Override
    public String toString() {
        return nom;
    }
}
