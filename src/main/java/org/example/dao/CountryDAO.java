package org.example.dao;


import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CountryDAO {
    private final DatabaseService db;

    public CountryDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
        CREATE TABLE IF NOT EXISTS countries (
            country_name VARCHAR(100) PRIMARY KEY
        )
        """;
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create countries table", e);
        }
    }

    /**
     * Returns all countries ordered alphabetically.
     */
    public List<String> findAll() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT country_name FROM countries ORDER BY country_name";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("country_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying countries", e);
        }
        return list;
    }
}
