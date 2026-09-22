package com.example.model;

import java.util.ArrayList;

public class Mansio {

    private final ArrayList<Zona> zones;
    private final ArrayList<Porta> portes;

    public Mansio() {
        this.zones = new ArrayList<>();
        this.portes = new ArrayList<>();
    }

    public void afegirZona(Zona zona) {
        zones.add(zona);
    }

    public void afegirPorta(Porta porta) {
        portes.add(porta);
    }

    public Zona obtenirZona(String nom) {
        for (Zona zona : zones) {
            if (zona.getNom().equalsIgnoreCase(nom)) {
                return zona;
            }
        }
        return null;
    }

    public ArrayList<Zona> getZones() {
        return zones;
    }

    public ArrayList<Porta> getPortes() {
        return portes;
    }

    public void inicialitzar() {
        Zona dormitori = new Zona("Dormitori Principal",
            "Sents un soroll estrany que ve del sud.");

        Zona despatx = new Zona("Despatx del Senyor",
            "Una llar de foc crema feblement a la paret nord.");

        afegirZona(dormitori);
        afegirZona(despatx);

        Porta dormitoriDespatx = new Porta("sud", dormitori, despatx);
        Porta despatxDormitori = new Porta("nord", despatx, dormitori);

        afegirPorta(dormitoriDespatx);
        afegirPorta(despatxDormitori);

        dormitori.afegirSortida(dormitoriDespatx);
        despatx.afegirSortida(despatxDormitori);
    }
}
