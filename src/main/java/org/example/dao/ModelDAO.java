package org.example.dao;

import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO {
    private final DatabaseService db;

    public ModelDAO(DatabaseService databaseService) {
        this.db = databaseService;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
      String sql= """
            CREATE TABLE IF NOT EXISTS modeller (
                model TEXT PRIMARY KEY
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("modeller tablosu oluşturulamadı", e);
        }
    }
    public List<String> findAllModels() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT model FROM modeller ORDER BY model";
        try {
            Connection c = db.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

  /*  public List<String> findAllModels() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT model FROM modeller ORDER BY model";
        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) list.add(rs.getString("model"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }*/
    public boolean insert(String model) {
        String sql = """
            INSERT INTO modeller(model)
            VALUES(?)
            ON CONFLICT(model) DO NOTHING
            """;
        try (Connection c = db.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, model);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


  /*  public boolean insert(String model) {
        String sql = """
            INSERT INTO modeller(model)
            VALUES()

            """;
        try (Connection c = db.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, model);

            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/
}

