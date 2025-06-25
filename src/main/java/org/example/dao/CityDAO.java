package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for city lookups by country.
 * Table: cities (name VARCHAR, country VARCHAR)
 */
public class CityDAO {
    private final DatabaseService db;

    public CityDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS cities (" +
                "name VARCHAR(100)," +
                "country VARCHAR(100)," +
                "PRIMARY KEY(name, country)" +
                ")";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create cities table", e);
        }
    }

    /**
     * Finds cities for given country.
     */
    public List<String> findByCountry(String country) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM cities WHERE country = ? ORDER BY name";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, country);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying cities", e);
        }
        return list;
    }
}
