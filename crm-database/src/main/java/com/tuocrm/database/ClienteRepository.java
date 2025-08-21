package com.tuocrm.database;

import com.tuocrm.core.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(String id);

    List<Cliente> findAll();

    void deleteById(String id);
}