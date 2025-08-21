package com.tuocrm.gui;

import com.tuocrm.core.CampagnaADS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CampagneADSController {

    @FXML
    private TableView<CampagnaADS> campagneTable;
    @FXML
    private TableColumn<CampagnaADS, String> nomeColumn;
    @FXML
    private TableColumn<CampagnaADS, String> piattaformaColumn;
    @FXML
    private TableColumn<CampagnaADS, Number> budgetColumn;
    @FXML
    private TableColumn<CampagnaADS, Number> leadsColumn;
    @FXML
    private TableColumn<CampagnaADS, String> statoColumn;

    private final ObservableList<CampagnaADS> listaCampagne = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        piattaformaColumn.setCellValueFactory(cellData -> cellData.getValue().piattaformaProperty());
        budgetColumn.setCellValueFactory(cellData -> cellData.getValue().budgetProperty());
        leadsColumn.setCellValueFactory(cellData -> cellData.getValue().leadGeneratiProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        caricaDatiDiProva();
        campagneTable.setItems(listaCampagne);
    }

    private void caricaDatiDiProva() {
        listaCampagne.add(new CampagnaADS("Sconto Estivo", "Meta Ads", 500, 32, "Attiva"));
        listaCampagne.add(new CampagnaADS("Lead Generation B2B", "Google Ads", 1200, 15, "Attiva"));
        listaCampagne.add(new CampagnaADS("Lancio Nuovo Prodotto", "LinkedIn Ads", 800, 22, "In Pausa"));
    }

    @FXML
    private void onNuovaCampagnaClicked() {
        CampagnaADS nuovaCampagna = apriDialogoCampagna(null);
        if (nuovaCampagna != null) {
            listaCampagne.add(nuovaCampagna);
        }
    }

    @FXML
    private void onModificaClicked() {
        CampagnaADS campagnaSelezionata = campagneTable.getSelectionModel().getSelectedItem();
        if (campagnaSelezionata == null) {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona una campagna da modificare.");
            return;
        }
        apriDialogoCampagna(campagnaSelezionata);
        campagneTable.refresh();
    }

    @FXML
    private void onEliminaClicked() {
        CampagnaADS campagnaSelezionata = campagneTable.getSelectionModel().getSelectedItem();
        if (campagnaSelezionata != null) {
            listaCampagne.remove(campagnaSelezionata);
        } else {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona una campagna da eliminare.");
        }
    }

    private CampagnaADS apriDialogoCampagna(CampagnaADS campagna) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuocrm/gui/add-campagna-dialog.fxml"));
            GridPane dialogPane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            AddCampagnaDialogController controller = loader.getController();
            if (campagna != null) {
                dialogStage.setTitle("Modifica Campagna");
                controller.setDati(campagna);
            } else {
                dialogStage.setTitle("Aggiungi Nuova Campagna");
            }

            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.showAndWait();
            return controller.getRisultato();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}