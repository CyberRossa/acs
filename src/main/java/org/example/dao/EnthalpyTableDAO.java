package org.example.dao;

import org.example.model.EnthalpyEntry;
import org.example.services.DatabaseService;
import java.sql.*;
import java.util.Optional;

public class EnthalpyTableDAO {
    private final DatabaseService db;

    public EnthalpyTableDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS enthalpy_table (
              kt                       DOUBLE,
              yt                       DOUBLE,
              fresh_air_ratio          DOUBLE,
              code                     VARCHAR PRIMARY KEY,
              taze_hava                DOUBLE,
              return_air               DOUBLE,
              return_air_enthalpy      DOUBLE,
              kt2                      DOUBLE,
              yt2                      DOUBLE,
              tmix_enthalpy            DOUBLE,
              processing_enthalpy_15c  DOUBLE,
              enthalpy_diff            DOUBLE
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create enthalpy_table", e);
        }
    }

    public Optional<EnthalpyEntry> findByConcate(String code) {
        String sql = "SELECT * FROM enthalpy_table WHERE code = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new EnthalpyEntry(
                            rs.getDouble("kt"),
                            rs.getDouble("yt"),
                            rs.getDouble("fresh_air_ratio"),
                            rs.getString("code"),
                            rs.getDouble("taze_hava"),
                            rs.getDouble("return_air"),
                            rs.getDouble("return_air_enthalpy"),
                            rs.getDouble("kt2"),
                            rs.getDouble("yt2"),
                            rs.getDouble("tmix_enthalpy"),
                            rs.getDouble("processing_enthalpy_15c"),
                            rs.getDouble("enthalpy_diff")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying enthalpy_table", e);
        }
        return Optional.empty();
    }
}
