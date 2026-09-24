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
        // Crear les zones de la mansió
        Zona dormitori = new Zona("Dormitori Principal",
                "Un dormitori ampli amb un llit de dosser i cortines de vellut vermell. La tempesta esgarrapa els vidres de la finestra. Al sud, una porta de fusta baixa al Despatx del Senyor.");

        Zona despatx = new Zona("Despatx del Senyor",
                "El cor administratiu de la mansió, amb prestatgeries de caoba i un gran escriptori. Al nord el Dormitori Principal, a l'oest el Vestuari i Armeria, a l'est els Banys de Teula i al sud el Gran Menjador.");

        Zona vestidor = new Zona("Vestuari i Armeria",
                "Una sala petita on es guarden els vestits de gala, abrics d'hivern i eines de caça. L'olor de cuir i cera omple l'aire. A l'est, una porta torna al Despatx del Senyor.");

        Zona bany = new Zona("Bany",
                "Una habitació revestida de teules blanques amb una banyera de ferro fosa amb potes de lleó i canonades de coure que vibren per la pressió de vapor. A l'oest, una porta torna al Despatx del Senyor.");

        Zona menjador = new Zona("Menjador",
                "Una gran taula de roure presideix la sala, apta per a dotze comensals. Un canelobre de cristall penja del sostre il·luminant feblement l'habitació. Al nord el Despatx del Senyor, a l'oest la Cuina, al sud el Saló de Recepció i a l'est el Taller de Mecànica.");

        Zona cuina = new Zona("Cuina",
                "La cuina de la casa, amb un fogó de carbó enorme. S'hi respira una olor dolça de rebosteria recent feta. A l'est, una porta torna al Gran Menjador.");

        Zona salo = new Zona("Saló de Recepció",
                "Un ampli saló per rebre visites il·lustres. A un racó hi ha una porta de ferro pesada que dona accés als nivells inferiors de la mansió. Al nord, una porta torna al Gran Menjador. Avall, una porta de ferro baixa a l'Escala del Celler.");
        Zona taller = new Zona("Taller de mecànica",
                "Un taller ple de motlles, engranatges i eines de ferro. Està completament a les fosques perquè el llum de gas s'ha apagat. Necessitareu una font de llum per veure-hi. A l'oest, una porta torna al Gran Menjador.");
        Zona escala = new Zona("Escala del Celler",
                "Un passadís humit i estret de pedra amb una escala de cargol que baixa cap a les fondàries de la mansió. La calor augmenta a cada pas. Amunt, l'escala puja al Saló de Recepció i al sud baixa al Celler de la Caldera.");
        Zona celler = new Zona("Celler de la Caldera",
                "La sala de màquines subterrània. Una enorme caldera de vapor d'acer xiula amb violència, deixant anar xorolls de vapor roent per les juntes danyades. Al nord, el passadís torna a l'Escala del Celler.");
        // Afegir les zones a la mansió
        afegirZona(dormitori);
        afegirZona(despatx);
        afegirZona(vestidor);
        afegirZona(bany);
        afegirZona(menjador);
        afegirZona(cuina);
        afegirZona(salo);
        afegirZona(taller);
        afegirZona(escala);
        afegirZona(celler);
        // Crear les portes entre les zones
        Porta dormitoriDespatx = new Porta("sud", dormitori, despatx);
        Porta despatxDormitori = new Porta("nord", despatx, dormitori);
        Porta despatxVestidor = new Porta("oest", despatx, vestidor);
        Porta vestidorDespatx = new Porta("est", vestidor, despatx);
        Porta despatxBany = new Porta("est", despatx, bany);
        Porta banyDespatx = new Porta("oest", bany, despatx);
        Porta menjadorDespatx = new Porta("nord", menjador, despatx);
        Porta despatxMenjador = new Porta("sud", despatx, menjador);
        Porta cuinaMenjador = new Porta("est", cuina, menjador);
        Porta menjadorCuina = new Porta("oest", menjador, cuina);
        Porta menjadorSalo = new Porta("sud", menjador, salo);
        Porta saloMenjador = new Porta("nord", salo, menjador);
        Porta tallerMenjador = new Porta("oest", taller, menjador);
        Porta menjadorTaller = new Porta("est", menjador, taller);
        Porta saloEscala = new Porta("avall", salo, escala, false, true);
        Porta escalaSalo = new Porta("amunt", escala, salo);
        Porta escalaCeller = new Porta("sud", escala, celler);
        Porta cellerEscala = new Porta("nord", celler, escala);
        // Afegir les portes a la mansió
        afegirPorta(dormitoriDespatx);
        afegirPorta(despatxDormitori);
        afegirPorta(despatxVestidor);
        afegirPorta(vestidorDespatx);
        afegirPorta(despatxBany);
        afegirPorta(banyDespatx);
        afegirPorta(menjadorDespatx);
        afegirPorta(despatxMenjador);
        afegirPorta(cuinaMenjador);
        afegirPorta(menjadorCuina);
        afegirPorta(menjadorSalo);
        afegirPorta(saloMenjador);
        afegirPorta(tallerMenjador);
        afegirPorta(menjadorTaller);
        afegirPorta(saloEscala);
        afegirPorta(escalaSalo);
        afegirPorta(escalaCeller);
        afegirPorta(cellerEscala);

        // Afegir les sortides a les zones
        dormitori.afegirSortida(dormitoriDespatx);
        despatx.afegirSortida(despatxDormitori);
        despatx.afegirSortida(despatxVestidor);
        vestidor.afegirSortida(vestidorDespatx);
        despatx.afegirSortida(despatxBany);
        bany.afegirSortida(banyDespatx);
        menjador.afegirSortida(menjadorDespatx);
        despatx.afegirSortida(despatxMenjador);
        cuina.afegirSortida(cuinaMenjador);
        menjador.afegirSortida(menjadorCuina);
        menjador.afegirSortida(menjadorSalo);
        salo.afegirSortida(saloMenjador);
        taller.afegirSortida(tallerMenjador);
        menjador.afegirSortida(menjadorTaller);
        salo.afegirSortida(saloEscala);
        escala.afegirSortida(escalaSalo);
        escala.afegirSortida(escalaCeller);
        celler.afegirSortida(cellerEscala);

        // Objecte clau per obrir la porta tancada del celler (Despatx, calaix escriptori)
        despatx.afegirObjecte(new Objecte("ClauDeCoure",
                "Una clau de coure massissa. Obre la porta de ferro del Saló que baixa a l'Escala del Celler.", true));
    }
}
