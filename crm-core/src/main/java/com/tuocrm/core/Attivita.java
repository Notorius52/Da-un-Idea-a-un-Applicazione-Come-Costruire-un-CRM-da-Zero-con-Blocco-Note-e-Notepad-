package com.tuocrm.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attivita {
    private final IntegerProperty id;
    private final StringProperty descrizione;
    private final StringProperty dataScadenza;
    private final StringProperty stato;
    private final IntegerProperty clienteId; // ID del cliente a cui è collegata

    public Attivita(int id, String descrizione, String dataScadenza, String stato, int clienteId) {
        this.id = new SimpleIntegerProperty(id);
        this.descrizione = new SimpleStringProperty(descrizione);
        this.dataScadenza = new SimpleStringProperty(dataScadenza);
        this.stato = new SimpleStringProperty(stato);
        this.clienteId = new SimpleIntegerProperty(clienteId);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public String getDescrizione() { return descrizione.get(); }
    public StringProperty descrizioneProperty() { return descrizione; }
    public String getDataScadenza() { return dataScadenza.get(); }
    public StringProperty dataScadenzaProperty() { return dataScadenza; }
    public String getStato() { return stato.get(); }
    public StringProperty statoProperty() { return stato; }
    public int getClienteId() { return clienteId.get(); }
    public IntegerProperty clienteIdProperty() { return clienteId; }
}