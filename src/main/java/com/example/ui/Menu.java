package com.example.ui;

import java.util.Scanner;

public class Menu {

    private static final String TITOL = "HISTORIA CONVERSACIONAL (1885)";

    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuInicial() {
        System.out.println();
        System.out.println(TITOL);
        System.out.println();
        System.out.println("Que desitges fer?");
        System.out.println();
        System.out.println("1. Nova Partida");
        System.out.println("2. Instruccions");
        System.out.println("3. Credits");
        System.out.println("4. Sortir");
        System.out.println();
        System.out.print("Selecciona una opcio (1-4): ");

        return llegirOpcio(1, 4);
    }

    public void mostrarInstruccions() {
        System.out.println();
        System.out.println("INSTRUCCIONS");
        System.out.println();
        System.out.println();
        System.out.println("COMANDES DISPONIBLES:");
        System.out.println();
        System.out.println("  ANAR [nord/sud/est/oest/amunt/avall] - Desplacar-te en una direcció");
        System.out.println("  AGAFAR [objecte]     - Agafar un objecte");
        System.out.println("  DEIXAR [objecte]     - Deixar un objecte a terra");
        System.out.println("  ENCENDRE [objecte]   - Encendre un objecte");
        System.out.println("  APAGAR [objecte]     - Apagar un objecte");
        System.out.println("  OBRIR [porta]        - Obrir una porta");
        System.out.println("  TANCAR [porta]       - Tancar una porta");
        System.out.println("  USAR [objecte]       - Usar un objecte");
        System.out.println("  PARLAR [texte]       - Parlar amb algu");
        System.out.println("  INVENTARI            - Veure els objectes que portes");
        System.out.println("  AJUDA                - Mostrar les comandes");
        System.out.println("  SORTIR               - Sortir del joc");
        System.out.println();
        System.out.println();
        System.out.println("Prem ENTER per tornar al menu...");
        scanner.nextLine();
    }

    public void mostrarCredits() {
        System.out.println();
        System.out.println("CREDITS");
        System.out.println();
        System.out.println("Historia Conversacional (1885)");
        System.out.println();
        System.out.println("Disseny: P00 - Projecte de Programacio");
        System.out.println("Motor: Java 17");
        System.out.println();
        System.out.println("Gracies per jugar!");
        System.out.println();
        System.out.println();
        System.out.println("Prem ENTER per tornar al menu...");
        scanner.nextLine();
    }

    public void mostrarMissatge(String missatge) {
        System.out.println();
        System.out.println("  " + missatge);
        System.out.println();
    }

    public String llegirOrdre() {
        System.out.print(" > ");
        String linia = scanner.nextLine().trim();
        if (linia.isEmpty()) {
            return null;
        }
        return linia.toLowerCase();
    }

    public String[] descomposarOrdre(String ordre) {
        String verb = "";
        String objectiu = "";
        String parametre = "";

        if (ordre == null || ordre.isEmpty()) {
            return new String[] { verb, objectiu, parametre };
        }

        String[] parts = ordre.split("\\s+", 4);

        if (parts.length >= 1) {
            verb = parts[0];
        }
        if (parts.length >= 3) {
            objectiu = parts[1];
            parametre = parts[2];
            if (parts.length == 4) {
                parametre = parts[2] + " " + parts[3];
            }
        } else if (parts.length == 2) {
            objectiu = parts[1];
        }

        return new String[] { verb, objectiu, parametre };
    }

    private int llegirOpcio(int min, int max) {
        int opcio = -1;
        while (opcio < min || opcio > max) {
            String entrada = scanner.nextLine().trim();
            try {
                opcio = Integer.parseInt(entrada);
                if (opcio < min || opcio > max) {
                    System.out.print("Opcio no valida. Torna a intentar (" + min + "-" + max + "): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Opcio no valida. Torna a intentar (" + min + "-" + max + "): ");
            }
        }
        return opcio;
    }

    public void tancar() {
        scanner.close();
    }
}
