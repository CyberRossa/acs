package org.example.dao;

import org.example.services.DatabaseService;
import java.sql.*;

public class EnthalpyTableDAO {
    private final DatabaseService db;

    public EnthalpyTableDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS enthalpy_table (
              dry_bulb_temp              DOUBLE,
              wet_bulb_temp              DOUBLE,
              fresh_air_ratio            DOUBLE,
              mixed_enthalpy             DOUBLE,
              return_air_flow            DOUBLE,
              return_air_enthalpy        DOUBLE,
              kt                         INT,
              yt                         INT,
              tmix_enthalpy              DOUBLE,
              processing_enthalpy_15c    DOUBLE,
              enthalpy_diff              DOUBLE,
              col12                      DOUBLE,
              PRIMARY KEY(dry_bulb_temp, wet_bulb_temp, fresh_air_ratio)
            )
            """;
        try (Connection c = db.getConnection();
             Statement s  = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create enthalpy_table", e);
        }
    }
}
