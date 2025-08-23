package com.tuocrm.gui;

import com.tuocrm.core.Cliente;
import com.tuocrm.core.Partner;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AddClienteDialogController {

    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField aziendaField;
    @FXML private TextField emailField;
    @FXML private TextField telefonoField;
    @FXML private TextField statoField;
    @FXML private ComboBox<Partner> partnerComboBox;

    private Cliente clienteDaModificare;
    private Cliente risultato;

    @FXML
    private void initialize() {
        partnerComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Partner partner) {
                return partner == null ? "Nessuno" : partner.getNome();
            }
            @Override
            public Partner fromString(String string) { return null; }
        });
    }

    public void setPartnerDisponibili(ObservableList<Partner> partners) {
        partnerComboBox.getItems().add(null);
        partnerComboBox.getItems().addAll(partners);
    }

    public void setDati(Cliente cliente, ObservableList<Partner> partners) {
        this.clienteDaModificare = cliente;
        setPartnerDisponibili(partners);
        nomeField.setText(cliente.getNome());
        cognomeField.setText(cliente.getCognome());
        aziendaField.setText(cliente.getAzienda());
        emailField.setText(cliente.getEmail());
        telefonoField.setText(cliente.getTelefono());
        statoField.setText(cliente.getStato());
        partners.stream()
               .filter(p -> p.getId() == cliente.getPartnerId())
               .findFirst()
               .ifPresent(partnerComboBox::setValue);
    }

    public Cliente getRisultato() { return risultato; }

    @FXML
    private void onSalvaClicked() {
        if (nomeField.getText().isEmpty()) {
            mostraAvviso("Campo Obbligatorio", "Il campo Nome è obbligatorio.");
            return;
        }
        Partner partnerSelezionato = partnerComboBox.getValue();
        int partnerId = (partnerSelezionato == null) ? 0 : partnerSelezionato.getId();
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String azienda = aziendaField.getText();
        String email = emailField.getText();
        String telefono = telefonoField.getText();
        String stato = statoField.getText();

        if (clienteDaModificare == null) {
            // CORREZIONE: Chiamata al costruttore con 8 argomenti
            risultato = new Cliente(0, nome, cognome, azienda, email, telefono, stato, partnerId);
        } else {
            clienteDaModificare.nomeProperty().set(nome);
            clienteDaModificare.cognomeProperty().set(cognome);
            clienteDaModificare.aziendaProperty().set(azienda);
            clienteDaModificare.emailProperty().set(email);
            clienteDaModificare.telefonoProperty().set(telefono);
            clienteDaModificare.statoProperty().set(stato);
            clienteDaModificare.setPartnerId(partnerId);
            risultato = clienteDaModificare;
        }
        closeStage();
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