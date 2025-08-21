package com.tuocrm.gui;

import com.tuocrm.core.Cliente;
import com.tuocrm.database.SqliteClienteRepository; // <-- Importa il nuovo repository
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardController {

    @FXML
    private TableView<Cliente> clientiTable;
    @FXML
    private TableColumn<Cliente, Number> idColumn;
    @FXML
    private TableColumn<Cliente, String> nomeColumn;
    @FXML
    private TableColumn<Cliente, String> cognomeColumn;
    @FXML
    private TableColumn<Cliente, String> aziendaColumn;
    @FXML
    private TableColumn<Cliente, String> statoColumn;

    private final ObservableList<Cliente> listaClienti = FXCollections.observableArrayList();
    private final SqliteClienteRepository repository = new SqliteClienteRepository(); // <-- Usa il repository SQL

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        cognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
        aziendaColumn.setCellValueFactory(cellData -> cellData.getValue().aziendaProperty());
        statoColumn.setCellValueFactory(cellData -> cellData.getValue().statoProperty());

        // Carica i dati dal database e li mostra nella tabella
        listaClienti.setAll(repository.findAll());
        clientiTable.setItems(listaClienti);
    }

    // Il metodo caricaDatiDiProva() non è più necessario e può essere cancellato.
}