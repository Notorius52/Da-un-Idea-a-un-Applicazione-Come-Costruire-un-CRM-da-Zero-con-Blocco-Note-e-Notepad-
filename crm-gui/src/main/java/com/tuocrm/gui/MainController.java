package com.tuocrm.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        onDashboardClicked();
    }

    @FXML
    public void onDashboardClicked() {
        System.out.println("Caricamento della Dashboard...");
        caricaVista("dashboard-view.fxml");
    }

    @FXML
    public void onLinkedInHubClicked() {
        System.out.println("Caricamento della vista LinkedIn Hub...");
        caricaVista("linkedin-hub-view.fxml");
    }

    @FXML
    public void onContentPlannerClicked() {
        System.out.println("Caricamento del Content Planner...");
        caricaVista("content-planner-view.fxml");
    }

    @FXML
    public void onCampagneADSClicked() {
        System.out.println("Caricamento del modulo Campagne ADS...");
        caricaVista("campagne-ads-view.fxml");
    }

    @FXML
    public void onPartnerReferralClicked() {
        System.out.println("Caricamento del modulo Partner e Referral...");
        caricaVista("partner-referral-view.fxml");
    }
	
	@FXML
    public void onClientiClicked() {
    System.out.println("Caricamento del modulo Clienti...");
    caricaVista("clienti-view.fxml");
    }
	
	@FXML
public void onAttivitaClicked() {
    System.out.println("Caricamento del modulo Attività...");
    caricaVista("attivita-view.fxml");
    } 

    private void caricaVista(String fxmlFile) {
        try {
            String pathToFxml = "/com/tuocrm/gui/" + fxmlFile;
            URL fileUrl = getClass().getResource(pathToFxml);

            if (fileUrl == null) {
                System.err.println("File FXML non trovato: " + pathToFxml);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            Parent view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento di " + fxmlFile);
            e.printStackTrace();
        }
    }
}