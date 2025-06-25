package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.model.PanelConfiguration;
import org.example.services.DatabaseService;
public class PanelConfigurationDAO {
    private final DatabaseService db;
    public PanelConfigurationDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS panel_configuration ("
                + "id SERIAL PRIMARY KEY, motor_power_kw DOUBLE, starting_method VARCHAR(50), "
                + "contactor_model VARCHAR(100), contactor_count INT, star_contactor_model VARCHAR(100), "
                + "mps_model VARCHAR(100), mps_setting_range VARCHAR(50), usd_exchange_rate DOUBLE, "
                + "motor_quantity INT, panel_price DOUBLE)";
        try (Connection c = db.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create panel_configuration table", e);
        }
    }
    public List<PanelConfiguration> findAll() {
        List<PanelConfiguration> list = new ArrayList<>();
        String sql = "SELECT * FROM panel_configuration";
        try (Connection c = db.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                PanelConfiguration pc = new PanelConfiguration();
                pc.setMotorPowerKw(rs.getDouble("motor_power_kw"));
                pc.setStartingMethod(rs.getString("starting_method"));
                pc.setContactorModel(rs.getString("contactor_model"));
                pc.setContactorCount(rs.getInt("contactor_count"));
                pc.setStarContactorModel(rs.getString("star_contactor_model"));
                pc.setMpsModel(rs.getString("mps_model"));
                pc.setMpsSettingRange(rs.getString("mps_setting_range"));
                pc.setUsdExchangeRate(rs.getDouble("usd_exchange_rate"));
                pc.setMotorQuantity(rs.getInt("motor_quantity"));
                pc.setPanelPrice(rs.getDouble("panel_price"));
                list.add(pc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
