package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahallDAO {
    private final DatabaseService db;

    public MahallDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS mahaller (
                zone_name VARCHAR(100) PRIMARY KEY,
                sector    VARCHAR(100)
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("mahaller tablosu oluşturulamadı", e);
        }
    }

    /** Tüm mahalleri döner (zone_name listesi) */
    public List<String> findAllZones() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT zone_name FROM mahaller ORDER BY zone_name";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("zone_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("mahaller sorgulanırken hata", e);
        }
        return list;
    }

    /** Bir mahale karşılık gelen sektörü döner */
    public String findSectorByZone(String zone) {
        String sql = "SELECT sector FROM mahaller WHERE zone_name = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, zone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sector");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("mahall sektörü sorgulanamadı", e);
        }
        return null;
    }
}
