package com.tuocrm.gui;

import com.tuocrm.core.Cliente;
import com.tuocrm.core.Partner;
import com.tuocrm.database.SqliteClienteRepository;
import com.tuocrm.database.SqlitePartnerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ClientiController {

    @FXML private TextField searchField;
    @FXML private TableView<Cliente> clientiTable;
    @FXML private TableColumn<Cliente, Number> idColumn;
    @FXML private TableColumn<Cliente, String> nomeColumn;
    @FXML private TableColumn<Cliente, String> cognomeColumn;
    @FXML private TableColumn<Cliente, String> aziendaColumn;
    @FXML private TableColumn<Cliente, String> statoColumn;

    private final ObservableList<Cliente> masterData = FXCollections.observableArrayList();
    private final SqliteClienteRepository repository = new SqliteClienteRepository();
    private final SqlitePartnerRepository partnerRepository = new SqlitePartnerRepository();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        cognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
        aziendaColumn.setCellValueFactory(cellData -> cellData.getValue().aziendaProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());
        
        masterData.setAll(repository.findAll());

        FilteredList<Cliente> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cliente.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getCognome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getAzienda().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        clientiTable.setItems(filteredData);
    }

    @FXML
    private void onNuovoClienteClicked() {
        Cliente nuovoCliente = apriDialogoCliente(null);
        if (nuovoCliente != null) {
            try {
                Cliente clienteSalvato = repository.save(nuovoCliente);
                if (clienteSalvato != null) {
                    masterData.add(clienteSalvato);
                }
            } catch (SQLException e) {
                gestisciErroreSql(e);
            }
        }
    }

    @FXML
    private void onModificaClienteClicked() {
        Cliente clienteSelezionato = clientiTable.getSelectionModel().getSelectedItem();
        if (clienteSelezionato == null) {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona un cliente da modificare.");
            return;
        }
        
        apriDialogoCliente(clienteSelezionato);
        
        try {
            repository.update(clienteSelezionato);
            clientiTable.refresh();
        } catch (SQLException e) {
            gestisciErroreSql(e);
        }
    }

    @FXML
    private void onEliminaClienteClicked() {
        Cliente clienteSelezionato = clientiTable.getSelectionModel().getSelectedItem();
        if (clienteSelezionato != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.setHeaderText("Eliminare Cliente: " + clienteSelezionato.getNome() + " " + clienteSelezionato.getCognome());
            alert.setContentText("Sei sicuro di voler procedere?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    repository.delete(clienteSelezionato.getId());
                    masterData.remove(clienteSelezionato);
                } catch (SQLException e) {
                    mostraAvviso("Errore Database", "Impossibile eliminare il cliente.");
                    e.printStackTrace();
                }
            }
        } else {
            mostraAvviso("Nessuna Selezione", "Per favore, seleziona un cliente da eliminare.");
        }
    }

    private Cliente apriDialogoCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuocrm/gui/add-cliente-dialog.fxml"));
            GridPane dialogPane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            AddClienteDialogController controller = loader.getController();
            
            ObservableList<Partner> tuttiIPartner = FXCollections.observableArrayList(partnerRepository.findAll());
            controller.setPartnerDisponibili(tuttiIPartner);
            
            if (cliente != null) { 
                dialogStage.setTitle("Modifica Cliente");
                controller.setDati(cliente, tuttiIPartner);
            } else {
                dialogStage.setTitle("Aggiungi Nuovo Cliente");
            }
            
            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.showAndWait();
            
            return controller.getRisultato();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void gestisciErroreSql(SQLException e) {
        if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
            mostraAvviso("Errore di Salvataggio", "Esiste già un record con questa email.");
        } else {
            mostraAvviso("Errore Database", "Si è verificato un errore sconosciuto.");
        }
        e.printStackTrace();
    }

    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}