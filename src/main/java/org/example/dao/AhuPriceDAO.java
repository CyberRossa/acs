package org.example.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.AhuPrice;
import org.example.services.DatabaseService;
public class AhuPriceDAO {
    private final DatabaseService db;
    public AhuPriceDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS ahu_price ("
                + "model_code VARCHAR(50) PRIMARY KEY, price DOUBLE)";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create ahu_price table", e);
        }
    }
    public List<AhuPrice> findAll() {
        List<AhuPrice> list = new ArrayList<>();
        String sql = "SELECT * FROM ahu_price";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                AhuPrice ap = new AhuPrice();
                ap.setModelCode(rs.getString("model_code"));
                ap.setPrice(rs.getDouble("price"));
                list.add(ap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public AhuPrice findByModel(String code) {
        String sql = "SELECT * FROM ahu_price WHERE model_code = ?";
        try (Connection c = db.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AhuPrice ap = new AhuPrice();
                    ap.setModelCode(rs.getString("model_code"));
                    ap.setPrice(rs.getDouble("price"));
                    return ap;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
