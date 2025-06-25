package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceModelDAO {
    private final DatabaseService db;

    public DeviceModelDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS device_models ("
                + "model_code VARCHAR(50) PRIMARY KEY)";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create device_models table", e);
        }
    }

    /**
     * Insert a model code if not exists.
     */
    public void insertModel(String code) {
        String sql = "MERGE INTO device_models KEY(model_code) VALUES(?)";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting device model", e);
        }
    }

    /**
     * Returns all model codes.
     */
    public List<String> findAllModels() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT model_code FROM device_models ORDER BY model_code";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("model_code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying device_models", e);
        }
        return list;
    }
}
