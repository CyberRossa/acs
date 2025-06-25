package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.Optional;

public class EnthalpyCodeDAO {
    private final DatabaseService db;

    public EnthalpyCodeDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS enthalpy_table (
                dry_bulb_temp DOUBLE,
                wet_bulb_temp DOUBLE,
                fresh_air_ratio DOUBLE,
                mixed_enthalpy DOUBLE,
                return_air_flow DOUBLE,
                return_air_enthalpy DOUBLE,
                col7 DOUBLE,
                col8 DOUBLE,
                col9 DOUBLE,
                col10 DOUBLE,
                col11 DOUBLE,
                col12 DOUBLE,
                col13 DOUBLE,
                col14 DOUBLE,
                col15 DOUBLE,
                col16 DOUBLE,
                PRIMARY KEY(dry_bulb_temp, wet_bulb_temp, fresh_air_ratio)
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create enthalpy_table", e);
        }
    }

    /**
     * Finds the enthalpy difference for the given composite code.
     * @param code composite key (e.g., kt+yt+freshRatio+blowOffset)
     * @return Optional enthalpy difference value
     */
    public Optional<Double> findEnthalpyDiff(String code) {
        String sql = "SELECT enthalpy_diff FROM enthalpy_code WHERE code = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getDouble("enthalpy_diff"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying enthalpy_code table", e);
        }
        return Optional.empty();
    }
}
