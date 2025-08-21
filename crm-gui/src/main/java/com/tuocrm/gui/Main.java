package com.tuocrm.gui;

// Importa le classi necessarie da JavaFX
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Importa la nostra classe per la gestione del database
import com.tuocrm.database.DatabaseManager; // <-- IMPORT MANCANTE, AGGIUNTO QUI

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("CRM Professionale!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Questa riga ora funzionerà perché abbiamo importato la classe
        DatabaseManager.creaTabelleSeNonEsistono();
        launch();
    }
}