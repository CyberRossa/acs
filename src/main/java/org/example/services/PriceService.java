package org.example.services;

import org.example.model.PriceData;
import java.sql.*;

public class PriceService {

    private final DatabaseService databaseService;
    private final LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public DatabaseService getDatabaseService() {
        return databaseService;
    }

    public PriceService(DatabaseService databaseService, LogService logService) {
        this.databaseService = databaseService;
        this.logService = logService;
        initializeDefaultPrices();
    }

    private void initializeDefaultPrices() {
        try (Connection conn = databaseService.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM prices");
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO prices (id, ahu, condensing, pano) VALUES (1, ?, ?, ?)");
                insertStmt.setDouble(1, 20000);
                insertStmt.setDouble(2, 15000);
                insertStmt.setDouble(3, 5000);
                insertStmt.executeUpdate();
                insertStmt.close();
                logService.log("INFO", "Varsayılan fiyatlar oluşturuldu.");
            }
            rs.close();
            checkStmt.close();
        } catch (SQLException e) {
            logService.log("ERROR", "Default fiyat oluşturulurken hata: " + e.getMessage());
        }
    }

    public PriceData getCurrentPrices() {
        PriceData priceData = new PriceData();
        try (Connection conn = databaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM prices WHERE id = 1");
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                priceData.ahuPrice = rs.getDouble("ahu");
                priceData.condensingPrice = rs.getDouble("condensing");
                priceData.panoPrice = rs.getDouble("pano");
            }
        } catch (SQLException e) {
            logService.log("ERROR", "Fiyat okunurken hata: " + e.getMessage());
        }
        return priceData;
    }

    public void updatePrices(double ahu, double condensing, double pano) {
        try (Connection conn = databaseService.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE prices SET ahu = ?, condensing = ?, pano = ? WHERE id = 1")) {
            stmt.setDouble(1, ahu);
            stmt.setDouble(2, condensing);
            stmt.setDouble(3, pano);
            stmt.executeUpdate();
            logService.log("INFO", "Fiyatlar güncellendi: AHU=" + ahu + ", Condensing=" + condensing + ", Pano=" + pano);
        } catch (SQLException e) {
            logService.log("ERROR", "Fiyat güncellenirken hata: " + e.getMessage());
        }
    }

    public double getAhuPrice() {
        return getCurrentPrices().ahuPrice;
    }

    public double getCondensingPrice() {
        return getCurrentPrices().condensingPrice;
    }

    public double getPanoPrice() {
        return getCurrentPrices().panoPrice;
    }

    public void setAhuPrice(double ahu) {
        PriceData current = getCurrentPrices();
        updatePrices(ahu, current.condensingPrice, current.panoPrice);
    }

    public void setCondensingPrice(double condensing) {
        PriceData current = getCurrentPrices();
        updatePrices(current.ahuPrice, condensing, current.panoPrice);
    }

    public void setPanoPrice(double pano) {
        PriceData current = getCurrentPrices();
        updatePrices(current.ahuPrice, current.condensingPrice, pano);
    }
}


