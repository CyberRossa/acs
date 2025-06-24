package org.example.dao;


import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class CityDAO {
        private final DatabaseService db;

        public CityDAO(DatabaseService databaseService) {
            this.db = databaseService;
            createTableIfNotExists();
        }

        private void createTableIfNotExists() {
            String sql = """
            CREATE TABLE IF NOT EXISTS cities (
              id             INTEGER PRIMARY KEY AUTOINCREMENT,
              country_code   TEXT NOT NULL,
              name           TEXT NOT NULL,
              FOREIGN KEY(country_code) REFERENCES countries(code)
            );
            """;
            try (Connection c = db.getConnection();
                 Statement s = c.createStatement()) {
                s.execute(sql);
            } catch (SQLException e) {
                throw new RuntimeException("cities tablosu oluşturulamadı", e);
            }
        }

        /** Belirli bir ülke koduna ait şehir isimlerini döner */
        public List<String> findByCountry(String countryCode) {
            List<String> cities = new ArrayList<>();
            String sql = "SELECT name FROM cities WHERE country_code=? ORDER BY name";
            try (Connection c = db.getConnection();
                 PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, countryCode);
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) {
                        cities.add(rs.getString("name"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cities;
        }

        public boolean insert(String countryCode, String cityName) {
            String sql = "INSERT INTO cities(country_code, name) VALUES(?,?)";
            try (Connection c = db.getConnection();
                 PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, countryCode);
                p.setString(2, cityName);
                return p.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

