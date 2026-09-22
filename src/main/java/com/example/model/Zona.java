package com.example.model;

import java.util.ArrayList;

public class Zona {

    private String nom;
    private String descripcio;
    private boolean fosca;
    private ArrayList<Objecte> objectes;
    private ArrayList<Porta> sortides;

    public Zona(String nom, String descripcio) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.fosca = false;
        this.objectes = new ArrayList<>();
        this.sortides = new ArrayList<>();
    }

    public Zona(String nom, String descripcio, boolean fosca) {
        this(nom, descripcio);
        this.fosca = fosca;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public boolean isFosca() {
        return fosca;
    }

    public void setFosca(boolean fosca) {
        this.fosca = fosca;
    }

    public ArrayList<Objecte> getObjectes() {
        return objectes;
    }

    public ArrayList<Porta> getSortides() {
        return sortides;
    }

    public void afegirObjecte(Objecte objecte) {
        objectes.add(objecte);
    }

    public void eliminarObjecte(Objecte objecte) {
        objectes.remove(objecte);
    }

    public void afegirSortida(Porta porta) {
        sortides.add(porta);
    }

    public Porta buscarPortaPerDireccio(String direccio) {
        for (Porta porta : sortides) {
            if (porta.getDireccio().equalsIgnoreCase(direccio)) {
                return porta;
            }
        }
        return null;
    }

    public String mostrarDescripcio() {
        StringBuilder sb = new StringBuilder();
        sb.append(nom).append("\n");
        sb.append("   ").append(descripcio).append("\n");

        if (fosca) {
            sb.append("   [FOSC] Es completament fosc. No veus res.\n");
        }

        if (!objectes.isEmpty()) {
            sb.append("   Objectes: ");
            for (int i = 0; i < objectes.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(objectes.get(i).getNom());
            }
            sb.append("\n");
        }

        if (!sortides.isEmpty()) {
            sb.append("   Sortides: ");
            for (int i = 0; i < sortides.size(); i++) {
                if (i > 0) sb.append(", ");
                Porta p = sortides.get(i);
                sb.append(p.getDireccio());
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Objecte buscarObjecte(String nomObjecte) {
        for (Objecte objecte : objectes) {
            if (objecte.getNom().equalsIgnoreCase(nomObjecte)) {
                return objecte;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nom;
    }
}
