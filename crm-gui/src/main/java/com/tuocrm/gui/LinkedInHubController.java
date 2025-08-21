package com.tuocrm.gui;

import com.tuocrm.core.LeadLinkedIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkedInHubController {

    @FXML
    private TableView<LeadLinkedIn> leadsTable;
    @FXML
    private TableColumn<LeadLinkedIn, String> nomeLeadColumn;
    @FXML
    private TableColumn<LeadLinkedIn, String> aziendaLeadColumn;
    @FXML
    private TableColumn<LeadLinkedIn, String> statoLeadColumn;

    private final ObservableList<LeadLinkedIn> listaLeads = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nomeLeadColumn.setCellValueFactory(cellData -> cellData.getValue().nomeContattoProperty());
        aziendaLeadColumn.setCellValueFactory(cellData -> cellData.getValue().aziendaProperty());
        statoLeadColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        caricaDatiDiProva();
        leadsTable.setItems(listaLeads);
    }

    private void caricaDatiDiProva() {
        listaLeads.add(new LeadLinkedIn("Laura Bianchi", "Tech Solutions", "Da contattare"));
        listaLeads.add(new LeadLinkedIn("Marco Neri", "Innovate Inc.", "In conversazione"));
        listaLeads.add(new LeadLinkedIn("Giulia Gialli", "Marketing Masters", "Appuntamento fissato"));
    }

    @FXML
    private void onAggiungiLeadClicked() {
        LeadLinkedIn nuovoLead = apriDialogoLead(null);
        if (nuovoLead != null) {
            listaLeads.add(nuovoLead);
        }
    }

    @FXML
    private void onModificaLeadClicked() {
        LeadLinkedIn leadSelezionato = leadsTable.getSelectionModel().getSelectedItem();
        if (leadSelezionato == null) {
            System.out.println("Nessun lead selezionato da modificare!");
            return;
        }
        apriDialogoLead(leadSelezionato);
        leadsTable.refresh();
    }

    @FXML
    private void onEliminaLeadClicked() {
        LeadLinkedIn leadSelezionato = leadsTable.getSelectionModel().getSelectedItem();
        if (leadSelezionato != null) {
            listaLeads.remove(leadSelezionato);
        } else {
            System.out.println("Nessun lead selezionato per l'eliminazione!");
        }
    }

    private LeadLinkedIn apriDialogoLead(LeadLinkedIn lead) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-lead-dialog.fxml"));
            GridPane dialogPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            AddLeadDialogController controller = loader.getController();

            if (lead != null) {
                dialogStage.setTitle("Modifica Lead");
                controller.setDati(lead);
            } else {
                dialogStage.setTitle("Aggiungi Nuovo Lead");
            }

            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

            return controller.getRisultato();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}