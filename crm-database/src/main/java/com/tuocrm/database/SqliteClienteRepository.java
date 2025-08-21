package com.tuocrm.database;

import com.tuocrm.core.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteClienteRepository { // <-- MODIFICA QUI

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clienti";
        List<Cliente> clienti = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                clienti.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("azienda"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("stato")
                ));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return clienti;
    }
    
    // In futuro, aggiungeremo qui i metodi save, update e delete per i clienti
}