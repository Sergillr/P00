package com.example.model;

import java.util.ArrayList;

public class Inventari {

    private final ArrayList<Objecte> objectes;

    public Inventari() {
        this.objectes = new ArrayList<>();
    }

    public boolean afegir(Objecte objecte) {
        return objectes.add(objecte);
    }

    public boolean eliminar(Objecte objecte) {
        return objectes.remove(objecte);
    }

    public boolean conte(String nomObjecte) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equalsIgnoreCase(nomObjecte)) {
                return true;
            }
        }
        return false;
    }

    public Objecte obtenir(String nom) {
        for (Objecte obj : objectes) {
            if (obj.getNom().equalsIgnoreCase(nom)) {
                return obj;
            }
        }
        return null;
    }

    public ArrayList<Objecte> getObjectes() {
        return objectes;
    }

    public void mostrar() {
        System.out.println();
        System.out.println("  INVENTARI:");
        if (objectes.isEmpty()) {
            System.out.println("     (buit)");
        } else {
            for (Objecte obj : objectes) {
                System.out.println("     - " + obj.getNom() + " - " + obj.getDescripcio());
            }
        }
        System.out.println();
    }
}
