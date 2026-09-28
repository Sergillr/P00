package com.example.model.personatges;

import com.example.model.Mansio;
import com.example.model.Personatge;
import com.example.model.Porta;
import com.example.model.Zona;
import com.example.model.objecte.LlanternaDeQuerosè;

public class Majordom extends Personatge {

    private final Mansio mansio;

    public Majordom(Zona zonaInicial, Mansio mansio) {
        super("Majordom", zonaInicial);
        this.mansio = mansio;
    }

    public String donarPistaLlanterna() {
        for (Zona zona : mansio.getZones()) {
            if (zona.buscarObjecte("LlanternaDeQuerosè") != null) {
                return "La llanterna de querosè és a " + zona.getNom() + ".";
            }
        }
        return "No sé on és la llanterna... potser algú se l'ha emportat.";
    }

    public boolean obrirPorta(Porta porta) {
        if (porta == null) {
            return false;
        }
        return porta.obrirPerMajordom();
    }

    public String dirUbicacioBestia(Bestia bestia) {
        if (bestia == null || bestia.getZonaActual() == null) {
            return "No sé on s'amaga la Bèstia.";
        }
        return "La Bèstia s'amaga a " + bestia.getZonaActual().getNom() + ". Vigila.";
    }

    @Override
    public String parlar(String frase) {
        return "El Majordom, encara tremolós per la tempesta, xiuxiueja: \"" + donarPistaLlanterna() + "\"";
    }
}
