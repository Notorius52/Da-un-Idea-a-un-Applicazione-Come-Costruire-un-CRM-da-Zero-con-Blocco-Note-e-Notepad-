package com.tuocrm.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty cognome;
    private final StringProperty azienda;
    private final StringProperty email;
    private final StringProperty telefono;
    private final StringProperty stato;

    public Cliente(int id, String nome, String cognome, String azienda, String email, String telefono, String stato) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.azienda = new SimpleStringProperty(azienda);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
        this.stato = new SimpleStringProperty(stato);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getCognome() {
        return cognome.get();
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public String getAzienda() {
        return azienda.get();
    }

    public StringProperty aziendaProperty() {
        return azienda;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public String getStato() {
        return stato.get();
    }

    public StringProperty statoProperty() {
        return stato;
    }
}