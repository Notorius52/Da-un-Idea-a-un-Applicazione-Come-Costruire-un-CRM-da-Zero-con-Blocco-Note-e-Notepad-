package com.tuocrm.database;

import com.tuocrm.core.CampagnaADS;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteCampagneRepository {

    public List<CampagnaADS> findAll() {
        String sql = "SELECT * FROM campagne";
        List<CampagnaADS> campagne = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                campagne.add(new CampagnaADS(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("piattaforma"),
                        rs.getInt("budget"),
                        rs.getInt("lead_generati"),
                        rs.getString("stato")
                ));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return campagne;
    }

    public CampagnaADS save(CampagnaADS campagna) throws SQLException {
        String sql = "INSERT INTO campagne(nome, piattaforma, budget, lead_generati, stato) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, campagna.getNome());
            pstmt.setString(2, campagna.getPiattaforma());
            pstmt.setInt(3, campagna.getBudget());
            pstmt.setInt(4, campagna.getLeadGenerati());
            pstmt.setString(5, campagna.getStato());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new CampagnaADS(generatedKeys.getInt(1), campagna.getNome(), campagna.getPiattaforma(), campagna.getBudget(), campagna.getLeadGenerati(), campagna.getStato());
            }
        }
        return null;
    }

    public void update(CampagnaADS campagna) throws SQLException {
        String sql = "UPDATE campagne SET nome = ?, piattaforma = ?, budget = ?, lead_generati = ?, stato = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, campagna.getNome());
            pstmt.setString(2, campagna.getPiattaforma());
            pstmt.setInt(3, campagna.getBudget());
            pstmt.setInt(4, campagna.getLeadGenerati());
            pstmt.setString(5, campagna.getStato());
            pstmt.setInt(6, campagna.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM campagne WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}