package com.tuocrm.gui;

import com.tuocrm.core.Partner;
import com.tuocrm.database.SqlitePartnerRepository;
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

public class PartnerReferralController {

    @FXML
    private TableView<Partner> partnerTable;
    @FXML
    private TableColumn<Partner, String> nomeColumn;
    @FXML
    private TableColumn<Partner, String> aziendaColumn;
    @FXML
    private TableColumn<Partner, String> emailColumn;
    @FXML
    private TableColumn<Partner, Number> referralColumn;

    private final ObservableList<Partner> listaPartner = FXCollections.observableArrayList();
    private final SqlitePartnerRepository repository = new SqlitePartnerRepository();

    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        aziendaColumn.setCellValueFactory(cellData -> cellData.getValue().aziendaProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        referralColumn.setCellValueFactory(cellData -> cellData.getValue().referralGeneratiProperty());
        listaPartner.setAll(repository.findAll());
        partnerTable.setItems(listaPartner);
    }

    @FXML
    private void onNuovoPartnerClicked() {
        Partner nuovoPartner = apriDialogoPartner(null);
        if (nuovoPartner != null) {
            try {
                Partner partnerSalvato = repository.save(nuovoPartner);
                if (partnerSalvato != null) {
                    listaPartner.add(partnerSalvato);
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                    mostraAvviso(Alert.AlertType.ERROR, "Errore di Salvataggio", "Esiste già un partner con questa email.");
                } else {
                    mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Si è verificato un errore sconosciuto durante il salvataggio.");
                }
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onModificaClicked() {
        Partner partnerSelezionato = partnerTable.getSelectionModel().getSelectedItem();
        if (partnerSelezionato == null) {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Per favore, seleziona un partner da modificare.");
            return;
        }
        
        // Passiamo l'oggetto da modificare al dialogo
        apriDialogoPartner(partnerSelezionato);

        // Dopo che il dialogo si chiude, la logica di salvataggio è già nel controller del dialogo.
        // Dobbiamo solo aggiornare il DB e la tabella.
        try {
            repository.update(partnerSelezionato);
            partnerTable.refresh(); // Aggiorniamo la visualizzazione della tabella
        } catch (SQLException e) {
             if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                mostraAvviso(Alert.AlertType.ERROR, "Errore di Salvataggio", "Esiste già un altro partner con questa email.");
            } else {
                mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Si è verificato un errore sconosciuto durante l'aggiornamento.");
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminaClicked() {
        Partner partnerSelezionato = partnerTable.getSelectionModel().getSelectedItem();
        if (partnerSelezionato != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.setHeaderText("Eliminare Partner: " + partnerSelezionato.getNome());
            alert.setContentText("Sei sicuro di voler procedere? L'azione è irreversibile.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    repository.delete(partnerSelezionato.getId());
                    listaPartner.remove(partnerSelezionato);
                } catch (SQLException e) {
                    mostraAvviso(Alert.AlertType.ERROR, "Errore Database", "Impossibile eliminare il partner.");
                    e.printStackTrace();
                }
            }
        } else {
            mostraAvviso(Alert.AlertType.WARNING, "Nessuna Selezione", "Per favore, seleziona un partner da eliminare.");
        }
    }

    /**
     * CORREZIONE: Questo metodo ora restituisce un oggetto Partner, non un boolean.
     */
    private Partner apriDialogoPartner(Partner partner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuocrm/gui/add-partner-dialog.fxml"));
            GridPane dialogPane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            AddPartnerDialogController controller = loader.getController();
            
            if (partner != null) { 
                dialogStage.setTitle("Modifica Partner");
                controller.setDati(partner);
            } else {
                dialogStage.setTitle("Aggiungi Nuovo Partner");
            }
            
            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.showAndWait();
            
            // Restituisce l'oggetto Partner creato o modificato dal dialogo
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