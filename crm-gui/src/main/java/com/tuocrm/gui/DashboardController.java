package com.tuocrm.gui;

import com.tuocrm.core.CampagnaADS;
import com.tuocrm.core.Cliente;
import com.tuocrm.core.Partner;
import com.tuocrm.database.SqliteCampagneRepository;
import com.tuocrm.database.SqliteClienteRepository;
import com.tuocrm.database.SqlitePartnerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private Label kpiTotaleClienti;
    @FXML
    private Label kpiTotalePartner;
    @FXML
    private Label kpiCampagneAttive;
    @FXML
    private PieChart clientiStatusChart;
    @FXML
    private BarChart<String, Number> campagneLeadChart;

    private final SqliteClienteRepository clienteRepo = new SqliteClienteRepository();
    private final SqlitePartnerRepository partnerRepo = new SqlitePartnerRepository();
    private final SqliteCampagneRepository campagneRepo = new SqliteCampagneRepository();

    @FXML
    public void initialize() {
        caricaKPIs();
        caricaGraficoClienti();
        caricaGraficoCampagne();
    }

    private void caricaKPIs() {
        List<Cliente> clienti = clienteRepo.findAll();
        List<Partner> partners = partnerRepo.findAll();
        List<CampagnaADS> campagne = campagneRepo.findAll();

        kpiTotaleClienti.setText(String.valueOf(clienti.size()));
        kpiTotalePartner.setText(String.valueOf(partners.size()));
        long attive = campagne.stream().filter(c -> "Attiva".equalsIgnoreCase(c.getStato())).count();
        kpiCampagneAttive.setText(String.valueOf(attive));
    }

    private void caricaGraficoClienti() {
        List<Cliente> tuttiIClienti = clienteRepo.findAll();
        if (tuttiIClienti.isEmpty()) {
            clientiStatusChart.setTitle("Nessun cliente nel database");
            return;
        }

        Map<String, Long> conteggioPerStato = tuttiIClienti.stream()
                .collect(Collectors.groupingBy(Cliente::getStato, Collectors.counting()));

        ObservableList<PieChart.Data> datiGrafico = FXCollections.observableArrayList();
        conteggioPerStato.forEach((stato, conteggio) -> {
            datiGrafico.add(new PieChart.Data(stato + " (" + conteggio + ")", conteggio));
        });
        clientiStatusChart.setData(datiGrafico);
    }

    private void caricaGraficoCampagne() {
        List<CampagnaADS> campagne = campagneRepo.findAll();
        if (campagne.isEmpty()) {
            campagneLeadChart.setTitle("Nessuna campagna nel database");
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Lead Generati");
        for (CampagnaADS campagna : campagne) {
            series.getData().add(new XYChart.Data<>(campagna.getNome(), campagna.getLeadGenerati()));
        }
        campagneLeadChart.getData().add(series);
    }
}