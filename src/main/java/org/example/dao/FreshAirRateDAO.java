package org.example.dao;

import org.example.services.DatabaseService;
import java.sql.*;
import java.util.Optional;

public class FreshAirRateDAO {
    private final DatabaseService db;

    public FreshAirRateDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS fresh_air_rate ("
                + "country_name VARCHAR(100),"
                + "city_name VARCHAR(100),"
                + "sector VARCHAR(50),"
                + "rate_per_person DOUBLE,"
                + "PRIMARY KEY(country_name, city_name, sector)"
                + ")";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create fresh_air_rate table", e);
        }
    }

    /**
     * Finds the fresh air rate per person for given parameters.
     * @param country country name
     * @param city city name
     * @param sector zone/sector
     * @return Optional of rate (m3/s per person)
     */
    public Optional<Double> findRatePerPerson(String country, String city, String sector) {
        String sql = "SELECT rate_per_person FROM fresh_air_rate "
                + "WHERE country_name = ? AND city_name = ? AND sector = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, country);
            ps.setString(2, city);
            ps.setString(3, sector);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getDouble("rate_per_person"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying fresh_air_rate", e);
        }
        return Optional.empty();
    }
}
