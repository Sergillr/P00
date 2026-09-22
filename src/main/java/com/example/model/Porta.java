package com.example.model;

public class Porta {

    private String direccio;
    private Zona zonaOrigen;
    private Zona zonaDesti;
    private boolean oberta;
    private boolean requereixClau;

    public Porta(String direccio, Zona origen, Zona desti) {
        this.direccio = direccio;
        this.zonaOrigen = origen;
        this.zonaDesti = desti;
        this.oberta = true;
        this.requereixClau = false;
    }

    public Porta(String direccio, Zona origen, Zona desti, boolean oberta) {
        this(direccio, origen, desti);
        this.oberta = oberta;
    }

    public Porta(String direccio, Zona origen, Zona desti, boolean oberta, boolean requereixClau) {
        this(direccio, origen, desti, oberta);
        this.requereixClau = requereixClau;
    }

    public String getDireccio() {
        return direccio;
    }

    public Zona getZonaOrigen() {
        return zonaOrigen;
    }

    public Zona getZonaDesti() {
        return zonaDesti;
    }

    public boolean isOberta() {
        return oberta;
    }

    public void setOberta(boolean oberta) {
        this.oberta = oberta;
    }

    public boolean isRequereixClau() {
        return requereixClau;
    }

    public boolean obrir() {
        if (requereixClau) {
            return false;
        }
        oberta = true;
        return true;
    }

    public boolean obrirAmbClau() {
        oberta = true;
        return true;
    }

    public void tancar() {
        oberta = false;
    }

    public Zona obtenirDesti() {
        if (oberta) {
            return zonaDesti;
        }
        return null;
    }

    @Override
    public String toString() {
        return direccio + " -> " + zonaDesti.getNom() + " (" + (oberta ? "oberta" : "tancada") + ")";
    }
}
