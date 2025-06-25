package org.example.dao;

import org.example.services.DatabaseService;
import org.example.model.MahallCevrimEntry;
import java.sql.*;
import java.util.Optional;


public class MahallCevrimDAO {
    private final DatabaseService db;

    public MahallCevrimDAO(DatabaseService db) throws SQLException {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS mahal_cevrim_t_hava (
              zone_name VARCHAR PRIMARY KEY,
              per_person_rate DOUBLE,
              air_changes DOUBLE,
              pressure DOUBLE,
              multiplier DOUBLE
            )
            """;
        try (Connection c = db.getConnection();
             Statement  s = c.createStatement()) {
            s.execute(sql);
        }
    }
    public Optional<MahallCevrimEntry> findByZone(String zone) {
        String sql = """
            SELECT zone_name, per_person_rate, air_changes, pressure, multiplier
              FROM mahal_cevrim_t_hava
             WHERE zone_name = ?
            """;
        try (var c  = db.getConnection();
             var ps = c.prepareStatement(sql)) {
            ps.setString(1, zone);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new MahallCevrimEntry(
                            rs.getString("zone_name"),
                            rs.getDouble("per_person_rate"),
                            rs.getDouble("air_changes"),
                            rs.getDouble("pressure"),
                            rs.getDouble("multiplier")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying mahal_cevrim_t_hava", e);
        }
        return Optional.empty();
    }
}
