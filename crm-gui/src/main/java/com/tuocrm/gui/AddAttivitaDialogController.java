package com.tuocrm.gui;

import com.tuocrm.core.Attivita;
import com.tuocrm.core.Cliente;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddAttivitaDialogController {

    @FXML private TextField descrizioneField;
    @FXML private ComboBox<Cliente> clienteComboBox;
    @FXML private DatePicker dataPicker;
    @FXML private TextField statoField;

    private Attivita attivitaDaModificare;
    private Attivita risultato;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        clienteComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente == null ? "" : cliente.getId() + ": " + cliente.getNome() + " " + cliente.getCognome();
            }
            @Override
            public Cliente fromString(String string) { return null; }
        });
    }

    public void setClienti(ObservableList<Cliente> clienti) {
        clienteComboBox.setItems(clienti);
    }

    public void setDati(Attivita attivita, ObservableList<Cliente> clienti) {
        this.attivitaDaModificare = attivita;
        setClienti(clienti);
        descrizioneField.setText(attivita.getDescrizione());
        statoField.setText(attivita.getStato());
        if (attivita.getDataScadenza() != null && !attivita.getDataScadenza().isEmpty()) {
            dataPicker.setValue(LocalDate.parse(attivita.getDataScadenza(), formatter));
        }
        clienti.stream()
               .filter(c -> c.getId() == attivita.getClienteId())
               .findFirst()
               .ifPresent(clienteComboBox::setValue);
    }

    public Attivita getRisultato() { return risultato; }

    @FXML
    private void onSalvaClicked() {
        if (descrizioneField.getText().isEmpty() || clienteComboBox.getValue() == null) {
            mostraAvviso("Campi Obbligatori", "Descrizione e Cliente sono obbligatori.");
            return;
        }
        String descrizione = descrizioneField.getText();
        Cliente clienteSelezionato = clienteComboBox.getValue();
        String data = dataPicker.getValue() != null ? dataPicker.getValue().format(formatter) : "";
        String stato = statoField.getText();

        if (attivitaDaModificare == null) {
            risultato = new Attivita(0, descrizione, data, stato, clienteSelezionato.getId());
        } else {
            attivitaDaModificare.descrizioneProperty().set(descrizione);
            attivitaDaModificare.dataScadenzaProperty().set(data);
            attivitaDaModificare.statoProperty().set(stato);
            attivitaDaModificare.clienteIdProperty().set(clienteSelezionato.getId());
            risultato = attivitaDaModificare;
        }
        closeStage();
    }

    @FXML
    private void onAnnullaClicked() {
        risultato = null;
        closeStage();
    }

    private void closeStage() { ( (Stage) descrizioneField.getScene().getWindow() ).close(); }

    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}