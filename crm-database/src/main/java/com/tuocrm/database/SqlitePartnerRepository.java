package com.tuocrm.database;

import com.tuocrm.core.Partner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlitePartnerRepository {

    public List<Partner> findAll() {
        String sql = "SELECT * FROM partners";
        List<Partner> partners = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                partners.add(new Partner(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("azienda"),
                        rs.getString("email"),
                        rs.getInt("referral_generati")
                ));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return partners;
    }

    public Partner save(Partner partner) throws SQLException {
        String sql = "INSERT INTO partners(nome, azienda, email, referral_generati) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, partner.getNome());
            pstmt.setString(2, partner.getAzienda());
            pstmt.setString(3, partner.getEmail());
            pstmt.setInt(4, partner.getReferralGenerati());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return new Partner(generatedKeys.getInt(1), partner.getNome(), partner.getAzienda(), partner.getEmail(), partner.getReferralGenerati());
            }
        }
        return null;
    }

    public void update(Partner partner) throws SQLException {
        String sql = "UPDATE partners SET nome = ?, azienda = ?, email = ?, referral_generati = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partner.getNome());
            pstmt.setString(2, partner.getAzienda());
            pstmt.setString(3, partner.getEmail());
            pstmt.setInt(4, partner.getReferralGenerati());
            pstmt.setInt(5, partner.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM partners WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}