package com.tuocrm.gui;

import com.tuocrm.core.ContentPlan;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContentDialogController {

    @FXML
    private TextField titoloField;
    @FXML
    private TextField canaleField;
    @FXML
    private TextField dataField;
    @FXML
    private TextField statoField;

    private ContentPlan contentDaModificare;
    private ContentPlan risultato;

    // NUOVO: Metodo per pre-compilare i campi in modalità modifica
    public void setDati(ContentPlan content) {
        this.contentDaModificare = content;
        titoloField.setText(content.getTitolo());
        canaleField.setText(content.getCanale());
        dataField.setText(content.getDataPubblicazione());
        statoField.setText(content.getStato());
    }

    public ContentPlan getRisultato() {
        return risultato;
    }

    // AGGIORNATO: Ora gestisce sia l'aggiunta che la modifica
    @FXML
    private void onSalvaClicked() {
        String titolo = titoloField.getText();
        String canale = canaleField.getText();
        String data = dataField.getText();
        String stato = statoField.getText();

        if (contentDaModificare == null) {
            // Modalità AGGIUNGI
            risultato = new ContentPlan(titolo, canale, data, stato);
        } else {
            // Modalità MODIFICA
            contentDaModificare.titoloProperty().set(titolo);
            contentDaModificare.canaleProperty().set(canale);
            contentDaModificare.dataPubblicazioneProperty().set(data);
            contentDaModificare.statoProperty().set(stato);
            risultato = contentDaModificare;
        }
        closeStage();
    }

    @FXML
    private void onAnnullaClicked() {
        risultato = null;
        closeStage();
    }

    private void closeStage() {
        ( (Stage) titoloField.getScene().getWindow() ).close();
    }
}