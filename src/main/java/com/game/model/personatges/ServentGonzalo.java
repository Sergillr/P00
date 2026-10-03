package com.game.model.personatges;

import com.game.model.Personatge;
import com.game.model.Porta;
import com.game.model.Zona;
import com.game.model.objecte.GaletesDeTe;
import java.util.ArrayList;
import java.util.Random;

public class ServentGonzalo extends Personatge {

    private static final Random ALEATORI = new Random();
    private boolean despert;
    private boolean entretingut;

    public ServentGonzalo(Zona zonaInicial) {
        super("Servent Gonzalo", zonaInicial);
        this.despert = false;
        this.entretingut = false;
    }

    public boolean isDespert() {
        return despert;
    }

    public boolean isEntretingut() {
        return entretingut;
    }

    public void despertar() {
        despert = true;
    }

    public void moureAleatoriament() {
        Zona actual = getZonaActual();
        if (actual == null) {
            return;
        }
        ArrayList<Porta> obertes = new ArrayList<>();
        for (Porta porta : actual.getSortides()) {
            if (porta.isOberta()) {
                obertes.add(porta);
            }
        }
        if (obertes.isEmpty()) {
            return;
        }
        setZonaActual(obertes.get(ALEATORI.nextInt(obertes.size())).getZonaDesti());
    }

    public void menjarGaletes(GaletesDeTe galetes) {
        entretingut = true;
    }

    public void reposar() {
        entretingut = false;
    }

    @Override
    public String parlar(String frase) {
        if (!despert) {
            return "Zzz... (En Gonzalo dorm plàcidament.)";
        }
        return "Eh? Què...? Tu has vist les meves galetes? M'encanten els dolços!";
    }
}
