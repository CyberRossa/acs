package org.example.dao;

import org.example.model.LogEntry;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    private final DatabaseService databaseService;

    public LogDAO(DatabaseService databaseService) {
        this.databaseService = databaseService;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                logDate TEXT,
                logLevel TEXT,
                message TEXT
            );
        """;
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Log tablosu oluşturulamadı.", e);
        }
    }

    public boolean insertLog(String logDate, String logLevel, String message) {
        String sql = "INSERT INTO logs (logDate, logLevel, message) VALUES (?, ?, ?)";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, logDate);
            ps.setString(2, logLevel);
            ps.setString(3, message);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LogEntry> findAll() {
        List<LogEntry> list = new ArrayList<>();
        String sql = "SELECT logDate, logLevel, message FROM logs ORDER BY id DESC";
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new LogEntry(
                        rs.getString("logDate"),
                        rs.getString("logLevel"),
                        rs.getString("message")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean clearAll() {
        String sql = "DELETE FROM logs";
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
