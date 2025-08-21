package com.tuocrm.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContentPlan {

    private final StringProperty titolo;
    private final StringProperty canale; // Es. "Blog", "LinkedIn", "Facebook"
    private final StringProperty dataPubblicazione;
    private final StringProperty stato; // Es. "Bozza", "In revisione", "Pubblicato"

    public ContentPlan(String titolo, String canale, String dataPubblicazione, String stato) {
        this.titolo = new SimpleStringProperty(titolo);
        this.canale = new SimpleStringProperty(canale);
        this.dataPubblicazione = new SimpleStringProperty(dataPubblicazione);
        this.stato = new SimpleStringProperty(stato);
    }

    public String getTitolo() { return titolo.get(); }
    public StringProperty titoloProperty() { return titolo; }

    public String getCanale() { return canale.get(); }
    public StringProperty canaleProperty() { return canale; }

    public String getDataPubblicazione() { return dataPubblicazione.get(); }
    public StringProperty dataPubblicazioneProperty() { return dataPubblicazione; }

    public String getStato() { return stato.get(); }
    public StringProperty statoProperty() { return stato; }
}