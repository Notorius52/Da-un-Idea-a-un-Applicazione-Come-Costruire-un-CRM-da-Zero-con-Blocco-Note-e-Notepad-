package com.tuocrm.database;

import com.tuocrm.core.Cliente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryClienteRepository implements ClienteRepository {

    // CODICE CORRETTO ✅
private final Map<Integer, Cliente> clienti = new HashMap<>();

    @Override
    public Cliente save(Cliente cliente) {
        clienti.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(String id) {
        return Optional.ofNullable(clienti.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return clienti.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        clienti.remove(id);
    }
}