package com.tuocrm.gui;

import com.tuocrm.core.LeadLinkedIn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLeadDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField aziendaField;
    @FXML
    private TextField statoField;
    @FXML
    private Button salvaButton;

    // Questa variabile conterrà il lead da modificare, o sarà null se ne stiamo creando uno nuovo.
    private LeadLinkedIn leadDaModificare;

    // Questa variabile conterrà il risultato (nuovo o modificato) da restituire.
    private LeadLinkedIn risultato;

    /**
     * NUOVO METODO: Questo metodo viene chiamato dall'esterno (da LinkedInHubController)
     * per passare i dati del lead selezionato e pre-compilare i campi.
     */
    public void setDati(LeadLinkedIn lead) {
        this.leadDaModificare = lead;
        nomeField.setText(lead.getNomeContatto());
        aziendaField.setText(lead.getAzienda());
        statoField.setText(lead.getStato());
    }

    /**
     * Metodo che restituisce il lead (nuovo o modificato) alla schermata principale.
     */
    public LeadLinkedIn getRisultato() {
        return risultato;
    }

    @FXML
    private void onSalvaClicked() {
        String nome = nomeField.getText();
        String azienda = aziendaField.getText();
        String stato = statoField.getText();

        if (leadDaModificare == null) {
            // Modalità AGGIUNGI: creiamo un nuovo oggetto
            risultato = new LeadLinkedIn(nome, azienda, stato);
        } else {
            // Modalità MODIFICA: aggiorniamo l'oggetto esistente
            // (Le Properties di JavaFX aggiorneranno automaticamente la tabella!)
            leadDaModificare.nomeContattoProperty().set(nome);
            leadDaModificare.aziendaProperty().set(azienda);
            leadDaModificare.statoProperty().set(stato);
            risultato = leadDaModificare;
        }

        // Chiudiamo la finestra di dialogo
        closeStage();
    }

    @FXML
    private void onAnnullaClicked() {
        // Chiudiamo la finestra senza salvare
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) salvaButton.getScene().getWindow();
        stage.close();
    }
}