package com.tuocrm.gui;

import com.tuocrm.core.ContentPlan;
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

public class ContentPlannerController {

    @FXML
    private TableView<ContentPlan> contentTable;
    @FXML
    private TableColumn<ContentPlan, String> titoloColumn;
    @FXML
    private TableColumn<ContentPlan, String> canaleColumn;
    @FXML
    private TableColumn<ContentPlan, String> dataColumn;
    @FXML
    private TableColumn<ContentPlan, String> statoColumn;

    private final ObservableList<ContentPlan> listaContenuti = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // ... (codice invariato)
        titoloColumn.setCellValueFactory(cellData -> cellData.getValue().titoloProperty());
        canaleColumn.setCellValueFactory(cellData -> cellData.getValue().canaleProperty());
        dataColumn.setCellValueFactory(cellData -> cellData.getValue().dataPubblicazioneProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        caricaDatiDiProva();
        contentTable.setItems(listaContenuti);
    }

    private void caricaDatiDiProva() {
        // ... (codice invariato)
        listaContenuti.add(new ContentPlan("5 errori da evitare nel marketing B2B", "Blog", "2025-09-01", "Pubblicato"));
        listaContenuti.add(new ContentPlan("Come LinkedIn può aumentare le vendite", "LinkedIn", "2025-09-10", "In revisione"));
        listaContenuti.add(new ContentPlan("Video tutorial sul nuovo CRM", "YouTube", "2025-09-15", "Bozza"));
    }

    @FXML
    private void onNuovaIdeaClicked() {
        ContentPlan nuovoPiano = apriDialogoContent(null);
        if (nuovoPiano != null) {
            listaContenuti.add(nuovoPiano);
        }
    }

    // --- METODO "Modifica" IMPLEMENTATO ---
    @FXML
    private void onModificaClicked() {
        ContentPlan pianoSelezionato = contentTable.getSelectionModel().getSelectedItem();
        if (pianoSelezionato == null) {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona un contenuto da modificare.");
            return;
        }
        apriDialogoContent(pianoSelezionato);
        contentTable.refresh();
    }

    @FXML
    private void onEliminaClicked() {
        ContentPlan pianoSelezionato = contentTable.getSelectionModel().getSelectedItem();
        if (pianoSelezionato != null) {
            listaContenuti.remove(pianoSelezionato);
        } else {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona un contenuto da eliminare.");
        }
    }

    // --- NUOVI METODI HELPER ---
    private ContentPlan apriDialogoContent(ContentPlan content) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuocrm/gui/add-content-dialog.fxml"));
            GridPane dialogPane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            AddContentDialogController controller = loader.getController();
            if (content != null) {
                dialogStage.setTitle("Modifica Piano di Contenuto");
                controller.setDati(content);
            } else {
                dialogStage.setTitle("Aggiungi Nuova Idea");
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