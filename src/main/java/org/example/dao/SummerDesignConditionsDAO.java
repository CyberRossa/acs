package org.example.dao;

import org.example.model.SummerDesignCondition;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SummerDesignConditionsDAO {
    private final DatabaseService db;

    public SummerDesignConditionsDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS summer_conditions (
              city         VARCHAR(100) PRIMARY KEY,
              kt           INTEGER NOT NULL,
              yt           INTEGER NOT NULL,
              kt_yt_code   VARCHAR(20) NOT NULL
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("summer_conditions tablosu oluşturulamadı", e);
        }
    }

    public Optional<Integer> findKt(String city) {
        String sql = "SELECT kt FROM summer_conditions WHERE city = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(rs.getInt("kt"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying summer_conditions.kt", e);
        }
        return Optional.empty();
    }

    public Optional<Integer> findYt(String city) {
        String sql = "SELECT yt FROM summer_conditions WHERE city = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(rs.getInt("yt"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying summer_conditions.yt", e);
        }
        return Optional.empty();
    }

    /** Bu metot, kt_yt_code'u çekmek isteyen kodlar için */
    public Optional<String> findKtYtCode(String city) {
        String sql = "SELECT kt_yt_code FROM summer_conditions WHERE city = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(rs.getString("kt_yt_code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying summer_conditions.kt_yt_code", e);
        }
        return Optional.empty();
    }
}