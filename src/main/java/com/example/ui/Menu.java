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

    public static String obtenirInstruccions() {
        return """
                INSTRUCCIONS

                COMANDES DISPONIBLES:

                  ANAR [nord/sud/est/oest/amunt/avall] - Desplacar-te en una direcció
                  AGAFAR [objecte]     - Agafar un objecte
                  DEIXAR [objecte]     - Deixar un objecte a terra
                  ENCENDRE [objecte]   - Encendre un objecte
                  APAGAR [objecte]     - Apagar un objecte
                  OBRIR [porta]        - Obrir una porta
                  TANCAR [porta]       - Tancar una porta
                  USAR [objecte]       - Usar un objecte
                  PARLAR [texte]       - Parlar amb algu
                  INVENTARI            - Veure els objectes que portes
                  AJUDA                - Mostrar les comandes
                  SORTIR               - Sortir del joc""";
    }

    public void mostrarInstruccions() {
        System.out.println();
        System.out.println(obtenirInstruccions());
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
