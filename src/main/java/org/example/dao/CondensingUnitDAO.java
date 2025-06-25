package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.services.DatabaseService;
import org.example.model.CondensingUnit;
public class CondensingUnitDAO {
    private final DatabaseService db;
    public CondensingUnitDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS condensing_unit ("
                + "compressor_model VARCHAR(100), compressor_qty INT, "
                + "device_model VARCHAR(100), cooling_capacity DOUBLE, "
                + "heating_power DOUBLE, current DOUBLE, "
                + "unit_price DOUBLE, total_price DOUBLE, labor_cost DOUBLE, device_price DOUBLE, "
                + "PRIMARY KEY(device_model)"
                + ")";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create condensing_unit table", e);
        }
    }

    public List<CondensingUnit> findAll() {
        List<CondensingUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM condensing_unit";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                CondensingUnit cu = new CondensingUnit();
                cu.setCompressorModel(rs.getString("compressor_model"));
                cu.setCompressorQty(rs.getInt("compressor_qty"));
                cu.setDeviceModel(rs.getString("device_model"));
                cu.setCoolingCapacity(rs.getDouble("cooling_capacity"));
                cu.setHeatingPower(rs.getDouble("heating_power"));
                cu.setCurrent(rs.getDouble("current"));
                cu.setUnitPrice(rs.getDouble("unit_price"));
                cu.setTotalPrice(rs.getDouble("total_price"));
                cu.setLaborCost(rs.getDouble("labor_cost"));
                cu.setDevicePrice(rs.getDouble("device_price"));
                list.add(cu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public CondensingUnit findByDeviceModel(String model) {
        String sql = "SELECT * FROM condensing_unit WHERE device_model = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, model);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CondensingUnit cu = new CondensingUnit();
                    cu.setCompressorModel(rs.getString("compressor_model"));
                    cu.setCompressorQty(rs.getInt("compressor_qty"));
                    cu.setDeviceModel(rs.getString("device_model"));
                    cu.setCoolingCapacity(rs.getDouble("cooling_capacity"));
                    cu.setHeatingPower(rs.getDouble("heating_power"));
                    cu.setCurrent(rs.getDouble("current"));
                    cu.setUnitPrice(rs.getDouble("unit_price"));
                    cu.setTotalPrice(rs.getDouble("total_price"));
                    cu.setLaborCost(rs.getDouble("labor_cost"));
                    cu.setDevicePrice(rs.getDouble("device_price"));
                    return cu;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
