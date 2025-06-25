package org.example.dao;

import org.example.model.DeviceSelection;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceSelectionDAO {
    private final DatabaseService db;
    public DeviceSelectionDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS device_selection ("
                + "device_group VARCHAR(50), air_flow DOUBLE, enthalpy_wet DOUBLE, "
                + "enthalpy_dry DOUBLE, code VARCHAR(50) PRIMARY KEY, model_code VARCHAR(50), "
                + "price DOUBLE, motor_power DOUBLE, drive_type VARCHAR(20))";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create device_selection table", e);
        }
    }
    public List<DeviceSelection> findAll() {
        List<DeviceSelection> list = new ArrayList<>();
        String sql = "SELECT * FROM device_selection";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                DeviceSelection ds = new DeviceSelection();
                ds.setDeviceGroup(rs.getString("device_group"));
                ds.setAirFlow(rs.getDouble("air_flow"));
                ds.setEnthalpyWet(rs.getDouble("enthalpy_wet"));
                ds.setEnthalpyDry(rs.getDouble("enthalpy_dry"));
                ds.setCode(rs.getString("code"));
                ds.setModelCode(rs.getString("model_code"));
                ds.setPrice(rs.getDouble("price"));
                ds.setMotorPower(rs.getDouble("motor_power"));
                ds.setDriveType(rs.getString("drive_type"));
                list.add(ds);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
