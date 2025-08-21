package com.tuocrm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:crm_database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void creaTabelleSeNonEsistono() {
        String sqlPartner = "CREATE TABLE IF NOT EXISTS partners (" +
                            " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " nome TEXT NOT NULL," +
                            " azienda TEXT," +
                            " email TEXT UNIQUE," +
                            " referral_generati INTEGER DEFAULT 0" +
                            ");";

        String sqlClienti = "CREATE TABLE IF NOT EXISTS clienti (" +
                            " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " nome TEXT NOT NULL," +
                            " cognome TEXT," +
                            " azienda TEXT," +
                            " email TEXT," +
                            " telefono TEXT," +
                            " stato TEXT" +
                            ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Controllo e creazione tabelle...");
            stmt.execute(sqlPartner);
            stmt.execute(sqlClienti);
            System.out.println("Tabelle pronte.");

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione delle tabelle: " + e.getMessage());
        }
    }
}