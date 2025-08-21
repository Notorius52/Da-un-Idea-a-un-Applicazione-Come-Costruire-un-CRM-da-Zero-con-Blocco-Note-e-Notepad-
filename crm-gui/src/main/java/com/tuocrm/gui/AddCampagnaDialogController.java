package com.tuocrm.gui;

import com.tuocrm.core.CampagnaADS;
import javafx.fxml.FXML;
import javafx.scene.control.Alert; // <-- Importa la classe Alert
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCampagnaDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField piattaformaField;
    @FXML
    private TextField budgetField;
    @FXML
    private TextField leadsField;
    @FXML
    private TextField statoField;

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

    public CampagnaADS getRisultato() {
        return risultato;
    }

    @FXML
    private void onSalvaClicked() {
        // Validazione: controlla se i campi obbligatori sono vuoti
        if (nomeField.getText().isEmpty() || budgetField.getText().isEmpty() || leadsField.getText().isEmpty()) {
            mostraAvviso("Campi Obbligatori", "Per favore, compila almeno i campi Nome, Budget e Lead Generati.");
            return; // Interrompi l'esecuzione del metodo
        }

        try {
            String nome = nomeField.getText();
            String piattaforma = piattaformaField.getText();
            int budget = Integer.parseInt(budgetField.getText());
            int leads = Integer.parseInt(leadsField.getText());
            String stato = statoField.getText();

            if (campagnaDaModificare == null) {
                risultato = new CampagnaADS(nome, piattaforma, budget, leads, stato);
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
            // --- MODIFICA PRINCIPALE ---
            // Sostituiamo la stampa in console con una finestra di avviso
            mostraAvviso("Errore di Inserimento", "Budget e Lead Generati devono essere numeri validi (es. 500, 32).");
        }
    }

    @FXML
    private void onAnnullaClicked() {
        risultato = null;
        closeStage();
    }

    private void closeStage() {
        ( (Stage) nomeField.getScene().getWindow() ).close();
    }

    // NUOVO METODO HELPER per mostrare avvisi
    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}