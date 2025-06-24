package org.example.dao;

import org.example.model.MahalParameters;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahalDAO {
    private final DatabaseService db;

    public MahalDAO(DatabaseService databaseService) {
        this.db = databaseService;
        createTableIfNotExists();
    }
    public MahalParameters findParameters(String code) {
        String sql = "SELECT * FROM mahaller WHERE code=?";
        try (Connection c = db.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, code);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next()) return null;
                return new MahalParameters(
                        rs.getDouble("cycle_factor"),
                        // Konfor
                        rs.getDouble("comfort_fresh_per_person"),
                        rs.getDouble("comfort_pressure"),
                        rs.getDouble("comfort_multiplier"),
                        // Hijyen
                        rs.getDouble("hygiene_min_outside_pct"),
                        rs.getDouble("hygiene_min_fresh_pct"),
                        rs.getDouble("hygiene_pressure"),
                        rs.getDouble("hygiene_multiplier")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createTableIfNotExists() {
        String sql = """
            
                CREATE TABLE IF NOT EXISTS mahaller (
                code                          TEXT PRIMARY KEY,
                name                          TEXT   NOT NULL,
                sector                        TEXT,
                cycle_factor                  REAL,
                -- Konfor parametreleri
                comfort_fresh_per_person      REAL,
                comfort_pressure              REAL,
                comfort_multiplier            REAL,
                -- Hijyen parametreleri
                hygiene_min_outside_pct       REAL,
                hygiene_min_fresh_pct         REAL,
                hygiene_pressure              REAL,
                hygiene_multiplier            REAL
              );
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("mahaller tablosu oluşturulamadı", e);
        }
    }

    /** Tüm mahal kodlarını döner (örn. "A101", "B202", ...) */
    public List<String> findAllCodes() {
        List<String> codes = new ArrayList<>();
        String sql = "SELECT code FROM mahaller ORDER BY code";
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

    public boolean insert(String code, String name, String sector, double cycleFactor) {
        String sql = """
            INSERT INTO mahaller(code,name,sector,cycle_factor)
            VALUES(?,?,?,?)
            ON CONFLICT(code) DO NOTHING
            """;
        try (Connection c = db.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, code);
            p.setString(2, name);
            p.setString(3, sector);
            p.setDouble(4, cycleFactor);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


