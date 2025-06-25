package org.example.dao;


import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectorDAO {
    private final DatabaseService db;

    public SectorDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS sectors (" +
                "name VARCHAR(100) PRIMARY KEY" +
                ")";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create sectors table", e);
        }
    }

    /**
     * Returns all sectors.
     */
    public List<String> findAll() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM sectors ORDER BY name";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying sectors", e);
        }
        return list;
    }
}
