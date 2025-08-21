package com.tuocrm.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeadLinkedIn {

    private final StringProperty nomeContatto;
    private final StringProperty azienda;
    private final StringProperty stato; // Es. "Da contattare", "In conversazione", "Richiesto appuntamento"

    public LeadLinkedIn(String nomeContatto, String azienda, String stato) {
        this.nomeContatto = new SimpleStringProperty(nomeContatto);
        this.azienda = new SimpleStringProperty(azienda);
        this.stato = new SimpleStringProperty(stato);
    }

    public String getNomeContatto() { return nomeContatto.get(); }
    public StringProperty nomeContattoProperty() { return nomeContatto; }

    public String getAzienda() { return azienda.get(); }
    public StringProperty aziendaProperty() { return azienda; }

    public String getStato() { return stato.get(); }
    public StringProperty statoProperty() { return stato; }
}