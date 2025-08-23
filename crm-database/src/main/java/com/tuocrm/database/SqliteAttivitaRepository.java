package com.tuocrm.database;

import com.tuocrm.core.Attivita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteAttivitaRepository {

    public List<Attivita> findAll() {
        String sql = "SELECT * FROM attivita";
        List<Attivita> attivitaList = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                attivitaList.add(new Attivita(
                        rs.getInt("id"), rs.getString("descrizione"),
                        rs.getString("data_scadenza"), rs.getString("stato"),
                        rs.getInt("cliente_id")
                ));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return attivitaList;
    }

    public Attivita save(Attivita attivita) throws SQLException {
        String sql = "INSERT INTO attivita(descrizione, data_scadenza, stato, cliente_id) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, attivita.getDescrizione());
            pstmt.setString(2, attivita.getDataScadenza());
            pstmt.setString(3, attivita.getStato());
            pstmt.setInt(4, attivita.getClienteId());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Attivita(generatedKeys.getInt(1), attivita.getDescrizione(), attivita.getDataScadenza(), attivita.getStato(), attivita.getClienteId());
            }
        }
        return null;
    }

    public void update(Attivita attivita) throws SQLException {
        String sql = "UPDATE attivita SET descrizione = ?, data_scadenza = ?, stato = ?, cliente_id = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attivita.getDescrizione());
            pstmt.setString(2, attivita.getDataScadenza());
            pstmt.setString(3, attivita.getStato());
            pstmt.setInt(4, attivita.getClienteId());
            pstmt.setInt(5, attivita.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM attivita WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}