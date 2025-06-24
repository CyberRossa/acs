package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    private final DatabaseService db;

    public CountryDAO(DatabaseService databaseService) {
        this.db = databaseService;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            
                CREATE TABLE IF NOT EXISTS countries (
              code TEXT PRIMARY KEY,
              name TEXT NOT NULL
            );
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("countries tablosu oluşturulamadı", e);
        }
    }

    /**
     * Tüm ülke kodlarını döner (örn. "TR", "US", ...)
     */
    public List<String> findAllCodes() {
        List<String> codes = new ArrayList<>();
        String sql = "SELECT code FROM countries ORDER BY code";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                codes.add(rs.getString("code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codes;
    }

    /**
     * Tüm ülke isimlerini döner (örn. "Türkiye", "United States", ...)
     */
    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT name FROM countries ORDER BY name";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public boolean insert(String code, String name) {
        String sql = "INSERT OR IGNORE INTO countries(code,name) VALUES(?,?)";
        try (Connection c = db.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, code);
            p.setString(2, name);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    }