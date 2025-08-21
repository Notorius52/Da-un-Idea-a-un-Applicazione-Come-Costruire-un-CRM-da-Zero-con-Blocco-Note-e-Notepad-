package com.tuocrm.ui;

import com.tuocrm.core.Cliente;
import com.tuocrm.database.ClienteRepository;
import com.tuocrm.database.InMemoryClienteRepository;

public class Main {
    public static void main(String[] args) {
        ClienteRepository repository = new InMemoryClienteRepository();

        // Creiamo due clienti di prova con i dati corretti
        Cliente cliente1 = new Cliente(
            1, "Mario", "Rossi", "Rossi SRL", "mario.rossi@email.com", "123456789", "Lead"
        );
        repository.save(cliente1);

        Cliente cliente2 = new Cliente(
            2, "Luigi", "Verdi", "Verdi SPA", "luigi.verdi@email.com", "987654321", "In trattativa"
        );
        repository.save(cliente2);

        // Stampiamo tutti i clienti presenti nel repository
        System.out.println("Clienti nel repository:");
        for (Cliente c : repository.findAll()) {
            // APPLICHIAMO LA CORREZIONE FINALE A QUESTA RIGA
            System.out.println("- ID: " + String.valueOf(c.getId()) + ", Nome: " + c.getNome());
        }
    }
}