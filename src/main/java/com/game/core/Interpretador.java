package com.game.core;

import java.util.Arrays;

public class Interpretador {

    public Comanda interpretar(String entrada) {
        if (entrada == null || entrada.isBlank()) {
            return null;
        }
        String[] parts = entrada.trim().toLowerCase().split("\\s+");
        if (parts.length == 1 && esDireccio(parts[0])) {
            return new Comanda(Verb.ANAR, parts[0], "");
        }
        Verb verb = aVerb(parts[0]);
        if (verb == null) {
            return null;
        }
        String objectiu = parts.length > 1 ? parts[1] : "";
        String parametre = parts.length > 2 ? String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)) : "";
        return new Comanda(verb, objectiu, parametre);
    }

    private boolean esDireccio(String paraula) {
        return paraula.equals("nord") || paraula.equals("sud")
                || paraula.equals("est") || paraula.equals("oest")
                || paraula.equals("amunt") || paraula.equals("avall");
    }

    private Verb aVerb(String paraula) {
        switch (paraula) {
            case "anar": return Verb.ANAR;
            case "agafar": return Verb.AGAFAR;
            case "deixar": return Verb.DEIXAR;
            case "encendre": return Verb.ENCENDRE;
            case "apagar": return Verb.APAGAR;
            case "obrir": return Verb.OBRIR;
            case "tancar": return Verb.TANCAR;
            case "usar": return Verb.USAR;
            case "parlar": return Verb.PARLAR;
            default: return null;
        }
    }
}
