package com.tuocrm.gui;

import com.tuocrm.core.Partner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPartnerDialogController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField aziendaField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField referralField;

    private Partner partnerDaModificare;
    private Partner risultato;

    public void setDati(Partner partner) {
        this.partnerDaModificare = partner;
        nomeField.setText(partner.getNome());
        aziendaField.setText(partner.getAzienda());
        emailField.setText(partner.getEmail());
        referralField.setText(String.valueOf(partner.getReferralGenerati()));
    }

    public Partner getRisultato() {
        return risultato;
    }

    @FXML
    private void onSalvaClicked() {
        if (nomeField.getText().isEmpty() || referralField.getText().isEmpty()) {
            mostraAvviso("Campi Obbligatori", "Per favore, compila almeno Nome e Referral Generati.");
            return;
        }
        try {
            String nome = nomeField.getText();
            String azienda = aziendaField.getText();
            String email = emailField.getText();
            int referrals = Integer.parseInt(referralField.getText());

            if (partnerDaModificare == null) {
                risultato = new Partner(0, nome, azienda, email, referrals);
            } else {
                partnerDaModificare.nomeProperty().set(nome);
                partnerDaModificare.aziendaProperty().set(azienda);
                partnerDaModificare.emailProperty().set(email);
                partnerDaModificare.referralGeneratiProperty().set(referrals);
                risultato = partnerDaModificare;
            }
            closeStage();
        } catch (NumberFormatException e) {
            mostraAvviso("Errore di Formato", "Il campo 'Referral Generati' deve essere un numero.");
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
    
    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}