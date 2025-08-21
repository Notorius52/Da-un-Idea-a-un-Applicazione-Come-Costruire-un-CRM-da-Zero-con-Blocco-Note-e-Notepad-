package com.tuocrm.gui;

/**
 * Questa classe serve solo come punto di ingresso stabile per il file JAR eseguibile.
 * Chiama semplicemente il metodo main della nostra vera classe Main,
 * risolvendo alcuni problemi di caricamento di JavaFX con i "fat jar".
 */
public class Launcher {
    public static void main(String[] args) {
        // Lancia l'applicazione JavaFX principale
        Main.main(args);
    }
}