package com.tuocrm.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partner {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty azienda;
    private final StringProperty email;
    private final IntegerProperty referralGenerati;

    public Partner(int id, String nome, String azienda, String email, int referralGenerati) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.azienda = new SimpleStringProperty(azienda);
        this.email = new SimpleStringProperty(email);
        this.referralGenerati = new SimpleIntegerProperty(referralGenerati);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public String getNome() { return nome.get(); }
    public StringProperty nomeProperty() { return nome; }
    public String getAzienda() { return azienda.get(); }
    public StringProperty aziendaProperty() { return azienda; }
    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public int getReferralGenerati() { return referralGenerati.get(); }
    public IntegerProperty referralGeneratiProperty() { return referralGenerati; }
}