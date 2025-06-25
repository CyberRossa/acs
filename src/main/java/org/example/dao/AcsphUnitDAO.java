package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.AcsphUnit;
import org.example.services.DatabaseService;

public class AcsphUnitDAO {
    private final DatabaseService db;
    public AcsphUnitDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS acsph_units ("
                + "code VARCHAR(50) PRIMARY KEY, model VARCHAR(100), compressor_model VARCHAR(100), "
                + "compressor_qty INT, air_flow DOUBLE, heating_power DOUBLE, current DOUBLE, "
                + "outdoor_dry_temp DOUBLE, evaporation_capacity DOUBLE, condensation_capacity DOUBLE, "
                + "labor_price DOUBLE, device_price DOUBLE)";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create acsph_units table", e);
        }
    }
    public List<AcsphUnit> findAll() {
        List<AcsphUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM acsph_units";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                AcsphUnit au = new AcsphUnit();
                au.setCode(rs.getString("code"));
                au.setModel(rs.getString("model"));
                au.setCompressorModel(rs.getString("compressor_model"));
                au.setCompressorQty(rs.getInt("compressor_qty"));
                au.setAirFlow(rs.getDouble("air_flow"));
                au.setHeatingPower(rs.getDouble("heating_power"));
                au.setCurrent(rs.getDouble("current"));
                au.setOutdoorDryTemp(rs.getDouble("outdoor_dry_temp"));
                au.setEvaporationCapacity(rs.getDouble("evaporation_capacity"));
                au.setCondensationCapacity(rs.getDouble("condensation_capacity"));
                au.setLaborPrice(rs.getDouble("labor_price"));
                au.setDevicePrice(rs.getDouble("device_price"));
                list.add(au);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
