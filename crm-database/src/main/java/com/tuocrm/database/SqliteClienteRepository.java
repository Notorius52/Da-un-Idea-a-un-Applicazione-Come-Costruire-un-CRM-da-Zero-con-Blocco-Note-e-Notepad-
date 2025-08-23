package com.tuocrm.database;

import com.tuocrm.core.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteClienteRepository {

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clienti";
        List<Cliente> clienti = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                clienti.add(new Cliente(
                        rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"),
                        rs.getString("azienda"), rs.getString("email"), rs.getString("telefono"),
                        rs.getString("stato"), rs.getInt("partner_id") // <-- AGGIUNTO partner_id
                ));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return clienti;
    }

    public Cliente save(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clienti(nome, cognome, azienda, email, telefono, stato, partner_id) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCognome());
            pstmt.setString(3, cliente.getAzienda());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getStato());
            pstmt.setInt(7, cliente.getPartnerId()); // <-- AGGIUNTO partner_id
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Restituisce un nuovo oggetto Cliente con l'ID e tutti i dati
                return new Cliente(generatedKeys.getInt(1), cliente.getNome(), cliente.getCognome(), cliente.getAzienda(), cliente.getEmail(), cliente.getTelefono(), cliente.getStato(), cliente.getPartnerId());
            }
        }
        return null;
    }

    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE clienti SET nome = ?, cognome = ?, azienda = ?, email = ?, telefono = ?, stato = ?, partner_id = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCognome());
            pstmt.setString(3, cliente.getAzienda());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getStato());
            pstmt.setInt(7, cliente.getPartnerId()); // <-- AGGIUNTO partner_id
            pstmt.setInt(8, cliente.getId()); // L'ID ora è l'ottavo parametro
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM clienti WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}