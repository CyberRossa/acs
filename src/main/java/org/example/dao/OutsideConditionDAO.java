package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.Optional;

/**
 * DAO for external weather condition lookup.
 * Table: outside_condition(country_name, city_name, temperature)
 */
public class OutsideConditionDAO {
    private final DatabaseService db;

    public OutsideConditionDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS outside_condition ("
                + "country_name VARCHAR(100),"
                + "city_name VARCHAR(100),"
                + "temperature DOUBLE,"
                + "PRIMARY KEY(country_name, city_name)"
                + ")";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create outside_condition table", e);
        }
    }

    /**
     * Finds the outside temperature for given location.
     * @param country country name
     * @param city city name
     * @return Optional of temperature in °C
     */
    public Optional<Double> findTemperature(String country, String city) {
        String sql = "SELECT temperature FROM outside_condition "
                + "WHERE country_name = ? AND city_name = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, country);
            ps.setString(2, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getDouble("temperature"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying outside_condition", e);
        }
        return Optional.empty();
    }
}
