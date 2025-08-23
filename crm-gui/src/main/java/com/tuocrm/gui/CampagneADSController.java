package com.tuocrm.gui;

import com.tuocrm.core.CampagnaADS;
import com.tuocrm.database.SqliteCampagneRepository;
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

public class CampagneADSController {

    @FXML private TableView<CampagnaADS> campagneTable;
    @FXML private TableColumn<CampagnaADS, String> nomeColumn;
    @FXML private TableColumn<CampagnaADS, String> piattaformaColumn;
    @FXML private TableColumn<CampagnaADS, Number> budgetColumn;
    @FXML private TableColumn<CampagnaADS, Number> leadsColumn;
    @FXML private TableColumn<CampagnaADS, String> statoColumn;

    private final ObservableList<CampagnaADS> listaCampagne = FXCollections.observableArrayList();
    private final SqliteCampagneRepository repository = new SqliteCampagneRepository();

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        piattaformaColumn.setCellValueFactory(cellData -> cellData.getValue().piattaformaProperty());
        budgetColumn.setCellValueFactory(cellData -> cellData.getValue().budgetProperty());
        leadsColumn.setCellValueFactory(cellData -> cellData.getValue().leadGeneratiProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        
        listaCampagne.setAll(repository.findAll());
        campagneTable.setItems(listaCampagne);
    }

    @FXML
    private void onNuovaCampagnaClicked() {
        CampagnaADS nuovaCampagna = apriDialogoCampagna(null);
        if (nuovaCampagna != null) {
            try {
                CampagnaADS campagnaSalvata = repository.save(nuovaCampagna);
                if (campagnaSalvata != null) {
                    listaCampagne.add(campagnaSalvata);
                }
            } catch (SQLException e) {
                mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile salvare la campagna.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onModificaClicked() {
        CampagnaADS campagnaSelezionata = campagneTable.getSelectionModel().getSelectedItem();
        if (campagnaSelezionata == null) {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Seleziona una campagna da modificare.");
            return;
        }
        apriDialogoCampagna(campagnaSelezionata);
        try {
            repository.update(campagnaSelezionata);
            campagneTable.refresh();
        } catch (SQLException e) {
            mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile aggiornare la campagna.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminaClicked() {
        CampagnaADS campagnaSelezionata = campagneTable.getSelectionModel().getSelectedItem();
        if (campagnaSelezionata != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.setHeaderText("Eliminare Campagna: " + campagnaSelezionata.getNome());
            alert.setContentText("Sei sicuro di voler procedere?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    repository.delete(campagnaSelezionata.getId());
                    listaCampagne.remove(campagnaSelezionata);
                } catch (SQLException e) {
                    mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile eliminare la campagna.");
                    e.printStackTrace();
                }
            }
        } else {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Seleziona una campagna da eliminare.");
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
    
    private void mostraAvviso(Alert.AlertType tipo, String titolo, String messaggio) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Attenzione");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}