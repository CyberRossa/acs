package org.example.dao;

import org.example.model.IcmalRow;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IcmalDAO {

    private final DatabaseService databaseService;

    public IcmalDAO(DatabaseService databaseService) {
        this.databaseService = databaseService;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS icmal (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                device TEXT,
                capacity REAL,
                airflow REAL,
                power REAL,
                price REAL,
                total REAL
            );
        """;
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Icmal tablosu oluşturulamadı.", e);
        }
    }

    public boolean insert(IcmalRow row) {
        String sql = "INSERT INTO icmal (device, capacity, airflow, power, price, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, row.getDevice());
            ps.setDouble(2, row.getCapacity());
            ps.setDouble(3, row.getAirflow());
            ps.setDouble(4, row.getPower());
            ps.setDouble(5, row.getPrice());
            ps.setDouble(6, row.getTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<IcmalRow> findAll() {
        List<IcmalRow> rows = new ArrayList<>();
        String sql = "SELECT * FROM icmal";
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                IcmalRow row = new IcmalRow();
                row.setDevice(rs.getString("device"));
                row.setCapacity(rs.getDouble("capacity"));
                row.setAirflow(rs.getDouble("airflow"));
                row.setPower(rs.getDouble("power"));
                row.setPrice(rs.getDouble("price"));
                row.setTotal(rs.getDouble("total"));
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public boolean deleteAll() {
        String sql = "DELETE FROM icmal";
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
