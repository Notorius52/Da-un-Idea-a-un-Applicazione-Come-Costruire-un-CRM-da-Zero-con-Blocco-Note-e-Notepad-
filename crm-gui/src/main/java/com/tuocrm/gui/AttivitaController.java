package com.tuocrm.gui;

import com.tuocrm.core.Attivita;
import com.tuocrm.core.Cliente;
import com.tuocrm.database.SqliteAttivitaRepository;
import com.tuocrm.database.SqliteClienteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AttivitaController {

    @FXML private TableView<Attivita> attivitaTable;
    @FXML private TableColumn<Attivita, String> descrizioneColumn;
    @FXML private TableColumn<Attivita, Number> clienteIdColumn;
    @FXML private TableColumn<Attivita, String> dataScadenzaColumn;
    @FXML private TableColumn<Attivita, String> statoColumn;

    private final ObservableList<Attivita> listaAttivita = FXCollections.observableArrayList();
    private final SqliteAttivitaRepository repository = new SqliteAttivitaRepository();
    private final SqliteClienteRepository clienteRepository = new SqliteClienteRepository();

    @FXML
    public void initialize() {
        descrizioneColumn.setCellValueFactory(cellData -> cellData.getValue().descrizioneProperty());
        clienteIdColumn.setCellValueFactory(cellData -> cellData.getValue().clienteIdProperty());
        dataScadenzaColumn.setCellValueFactory(cellData -> cellData.getValue().dataScadenzaProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        
        listaAttivita.setAll(repository.findAll());
        attivitaTable.setItems(listaAttivita);
    }

    @FXML
    private void onNuovaAttivitaClicked() {
        Attivita nuovaAttivita = apriDialogoAttivita(null);
        if (nuovaAttivita != null) {
            try {
                Attivita attivitaSalvata = repository.save(nuovaAttivita);
                if (attivitaSalvata != null) {
                    listaAttivita.add(attivitaSalvata);
                }
            } catch (SQLException e) {
                mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile salvare l'attività.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onModificaClicked() {
        Attivita attivitaSelezionata = attivitaTable.getSelectionModel().getSelectedItem();
        if (attivitaSelezionata == null) {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Seleziona un'attività da modificare.");
            return;
        }
        apriDialogoAttivita(attivitaSelezionata);
        try {
            repository.update(attivitaSelezionata);
            attivitaTable.refresh();
        } catch (SQLException e) {
            mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile aggiornare l'attività.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminaClicked() {
        Attivita attivitaSelezionata = attivitaTable.getSelectionModel().getSelectedItem();
        if (attivitaSelezionata != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.setHeaderText("Eliminare Attività?");
            alert.setContentText(attivitaSelezionata.getDescrizione());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    repository.delete(attivitaSelezionata.getId());
                    listaAttivita.remove(attivitaSelezionata);
                } catch (SQLException e) {
                    mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile eliminare l'attività.");
                    e.printStackTrace();
                }
            }
        } else {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Seleziona un'attività da eliminare.");
        }
    }

    private Attivita apriDialogoAttivita(Attivita attivita) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuocrm/gui/add-attivita-dialog.fxml"));
            GridPane dialogPane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            AddAttivitaDialogController controller = loader.getController();
            
            ObservableList<Cliente> tuttiIClienti = FXCollections.observableArrayList(clienteRepository.findAll());
            controller.setClienti(tuttiIClienti);

            if (attivita != null) { 
                dialogStage.setTitle("Modifica Attività");
                controller.setDati(attivita, tuttiIClienti);
            } else {
                dialogStage.setTitle("Aggiungi Nuova Attività");
            }
            
            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.showAndWait();
            return controller.getRisultato();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void mostraAvviso(Alert.AlertType tipo, String titolo, String messaggio) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Attenzione");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}