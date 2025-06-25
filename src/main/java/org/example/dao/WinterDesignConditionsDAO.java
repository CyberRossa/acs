package org.example.dao;

import org.example.model.WinterDesignCondition;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WinterDesignConditionsDAO {
    private final DatabaseService db;

    public WinterDesignConditionsDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS winter_conditions (
              city       VARCHAR(100) PRIMARY KEY,
              kt         INTEGER NOT NULL
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("winter_conditions tablosu oluşturulamadı", e);
        }
    }

    /**
     * Finds winter outdoor temperature (KT) for given city.
     */
    public Optional<Integer> findKt(String city) {
        System.out.println("findKt called with city = '" + city + "'");
        String sql = "SELECT kt FROM winter_conditions WHERE city = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getInt("kt"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying winter_conditions", e);
        }
        return Optional.empty();
    }
}