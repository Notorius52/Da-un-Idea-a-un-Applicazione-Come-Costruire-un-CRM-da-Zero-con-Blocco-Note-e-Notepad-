package com.tuocrm.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CampagnaADS {

    private final StringProperty nome;
    private final StringProperty piattaforma; // Es. Google Ads, Meta Ads
    private final IntegerProperty budget;
    private final IntegerProperty leadGenerati;
    private final StringProperty stato; // Es. Attiva, In Pausa, Conclusa

    public CampagnaADS(String nome, String piattaforma, int budget, int leadGenerati, String stato) {
        this.nome = new SimpleStringProperty(nome);
        this.piattaforma = new SimpleStringProperty(piattaforma);
        this.budget = new SimpleIntegerProperty(budget);
        this.leadGenerati = new SimpleIntegerProperty(leadGenerati);
        this.stato = new SimpleStringProperty(stato);
    }

    public String getNome() { return nome.get(); }
    public StringProperty nomeProperty() { return nome; }

    public String getPiattaforma() { return piattaforma.get(); }
    public StringProperty piattaformaProperty() { return piattaforma; }

    public int getBudget() { return budget.get(); }
    public IntegerProperty budgetProperty() { return budget; }

    public int getLeadGenerati() { return leadGenerati.get(); }
    public IntegerProperty leadGeneratiProperty() { return leadGenerati; }

    public String getStato() { return stato.get(); }
    public StringProperty statoProperty() { return stato; }
}