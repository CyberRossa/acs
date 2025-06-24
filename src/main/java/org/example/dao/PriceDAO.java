package org.example.dao;

import org.example.model.PriceData;
import org.example.services.DatabaseService;

import java.sql.*;


public class PriceDAO {
    private final DatabaseService databaseService;

    public PriceDAO(DatabaseService databaseService) {
        this.databaseService = databaseService;
        createTableIfNotExists();
        ensureDefaultRow();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS prices (
                id          INTEGER PRIMARY KEY,
                ahu         REAL,
                condensing  REAL,
                pano        REAL
            );
        """;
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Prices tablosu oluşturulamadı.", e);
        }
    }

    private void ensureDefaultRow() {
        String check = "SELECT COUNT(*) FROM prices WHERE id=1";
        String insert = "INSERT INTO prices(id, ahu, condensing, pano) VALUES (1, 20000, 15000, 5000)";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(check);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(insert);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Default price satırı eklenemedi.", e);
        }
    }

    public PriceData getCurrentPrices() {
        PriceData pd = new PriceData();
        String sql = "SELECT ahu, condensing, pano FROM prices WHERE id=1";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                pd.ahuPrice        = rs.getDouble("ahu");
                pd.condensingPrice = rs.getDouble("condensing");
                pd.panoPrice       = rs.getDouble("pano");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pd;
    }

    public boolean updatePrices(double ahu, double condensing, double pano) {
        String sql = "UPDATE prices SET ahu=?, condensing=?, pano=? WHERE id=1";
        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, ahu);
            ps.setDouble(2, condensing);
            ps.setDouble(3, pano);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
