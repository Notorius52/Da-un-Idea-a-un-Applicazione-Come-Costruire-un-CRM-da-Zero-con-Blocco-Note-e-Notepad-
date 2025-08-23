package com.tuocrm.gui;

import com.tuocrm.core.CampagnaADS;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCampagnaDialogController {

    @FXML private TextField nomeField;
    @FXML private TextField piattaformaField;
    @FXML private TextField budgetField;
    @FXML private TextField leadsField;
    @FXML private TextField statoField;

    private CampagnaADS campagnaDaModificare;
    private CampagnaADS risultato;

    public void setDati(CampagnaADS campagna) {
        this.campagnaDaModificare = campagna;
        nomeField.setText(campagna.getNome());
        piattaformaField.setText(campagna.getPiattaforma());
        budgetField.setText(String.valueOf(campagna.getBudget()));
        leadsField.setText(String.valueOf(campagna.getLeadGenerati()));
        statoField.setText(campagna.getStato());
    }

    public CampagnaADS getRisultato() { return risultato; }

    @FXML
    private void onSalvaClicked() {
        if (nomeField.getText().isEmpty()) {
            mostraAvviso("Campo Obbligatorio", "Il campo Nome Campagna è obbligatorio.");
            return;
        }
        try {
            String nome = nomeField.getText();
            String piattaforma = piattaformaField.getText();
            int budget = Integer.parseInt(budgetField.getText());
            int leads = Integer.parseInt(leadsField.getText());
            String stato = statoField.getText();

            if (campagnaDaModificare == null) {
                risultato = new CampagnaADS(0, nome, piattaforma, budget, leads, stato);
            } else {
                campagnaDaModificare.nomeProperty().set(nome);
                campagnaDaModificare.piattaformaProperty().set(piattaforma);
                campagnaDaModificare.budgetProperty().set(budget);
                campagnaDaModificare.leadGeneratiProperty().set(leads);
                campagnaDaModificare.statoProperty().set(stato);
                risultato = campagnaDaModificare;
            }
            closeStage();
        } catch (NumberFormatException e) {
            mostraAvviso("Errore di Formato", "I campi Budget e Lead Generati devono essere numeri.");
        }
    }

    @FXML
    private void onAnnullaClicked() {
        risultato = null;
        closeStage();
    }

    private void closeStage() { ( (Stage) nomeField.getScene().getWindow() ).close(); }
    
    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}