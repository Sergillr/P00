package com.game.core;

public class Comanda {

    private final Verb verb;
    private final String objectiu;
    private final String parametre;

    public Comanda(Verb verb, String objectiu, String parametre) {
        this.verb = verb;
        this.objectiu = objectiu;
        this.parametre = parametre;
    }

    public Verb getVerb() {
        return verb;
    }

    public String getObjectiu() {
        return objectiu;
    }

    public String getParametre() {
        return parametre;
    }

    public String objectiuComplet() {
        if (parametre == null || parametre.isEmpty()) {
            return objectiu;
        }
        return objectiu + " " + parametre;
    }

    public void executar(Joc joc) {
        String desti = objectiuComplet();
        switch (verb) {
            case ANAR -> joc.moure(desti);
            case AGAFAR -> joc.agafar(desti);
            case DEIXAR -> joc.deixar(desti);
            case ENCENDRE -> joc.encendre(desti);
            case APAGAR -> joc.apagar(desti);
            case OBRIR -> joc.obrir(desti);
            case TANCAR -> joc.tancar(desti);
            case USAR -> joc.usar(desti);
            case PARLAR -> joc.parlar(desti);
        }
    }
}
