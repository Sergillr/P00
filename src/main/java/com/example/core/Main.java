package com.example.core;

import com.example.ui.Menu;

public class Main {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Menu menu = new Menu();
        Joc joc = new Joc();

        boolean executant = true;

        while (executant) {
            int opcio = menu.mostrarMenuInicial();

            switch (opcio) {
                case 1:
                    joc.iniciarPartida();
                    joc.executarPartida(menu);
                    break;
                case 2:
                    menu.mostrarInstruccions();
                    break;
                case 3:
                    menu.mostrarCredits();
                    break;
                case 4:
                    menu.mostrarMissatge("Adeu!");
                    executant = false;
                    break;
            }
        }

        menu.tancar();
    }
}
