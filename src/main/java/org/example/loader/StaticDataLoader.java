package org.example.loader;

import org.example.dao.*;
import org.example.model.User;
import org.example.services.DatabaseService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * Utility to load static CSV data into the database using JDBC.
 * Place CSV files under src/main/resources/csv/ with header row.
 */
public class StaticDataLoader {
    private final DatabaseService db;

    public StaticDataLoader(DatabaseService db) {
        this.db = db;
    }

    /**
     * Loads all static tables from CSVs at application startup.
     */
    public void loadAll() throws SQLException {
        new CountryDAO(db);
        new CityDAO(db);
        new MahallDAO(db);
        new SectorDAO(db);
        new MahallCevrimDAO(db);
        new EnthalpyTableDAO(db);
        new EnthalpyCodeDAO(db);
        new WinterDesignConditionsDAO(db);
        new SummerDesignConditionsDAO(db);
        new UserDAO(db);
     //   new DeviceModelDAO(db);
        UserDAO udao = new UserDAO(db);
        User admin = new User();
        admin.setUsername("admin");
        // BCrypt ile parolayı hash’le:
        String hash = org.mindrot.jbcrypt.BCrypt.hashpw("admin123", org.mindrot.jbcrypt.BCrypt.gensalt());
        admin.setPasswordHash(hash);
        admin.setRole("ADMIN");
        udao.insert(admin);

        loadCountries();
        loadCities();
        loadMahaller();
        loadMahalCevrim();
        loadEnthalpyTable();
        loadSummerConditions();
        loadWinterConditions();
       // loadDeviceSeriesModels();
        // add more as needed
    }

    private void loadCountries() {
        String sql = "MERGE INTO countries(country_name) KEY(country_name) VALUES(?)";
        loadCsvAndInsert("csv/countries.csv", sql, 1, (ps, cols) -> {
            ps.setString(1, cols[0]);
        });
    }

    private void loadCities() {
        String sql = "MERGE INTO cities(name,country) KEY(name,country) VALUES(?,?)";
        loadCsvAndInsert("csv/cities.csv", sql, 2, (ps, cols) -> {
            ps.setString(1, cols[1]);
            ps.setString(2, cols[0]);
        });
    }
    private void loadSummerConditions() {
        String sql = "MERGE INTO summer_conditions(city, kt, yt, kt_yt_code) "
                + "KEY(city) VALUES(?,?,?,?)";
        loadCsvAndInsert(
                "csv/summer.csv",  // bu, kaynak dosyanın adı
                sql,
                4,
                (ps, cols) -> {
                    ps.setString(1, cols[0].trim());                     // city
                    ps.setInt   (2, Integer.parseInt(cols[1].trim()));   // kt
                    ps.setInt   (3, Integer.parseInt(cols[2].trim()));   // yt
                    ps.setString(4, cols[3].trim());                     // kt_yt_code
                }
        );
    }




    private void loadMahaller() {
        // mahaller tablosunu DAO yaratıyor, şimdi MERGE ile yükleyelim
        String sql = "MERGE INTO mahaller(zone_name, sector) KEY(zone_name) VALUES(?,?)";
        loadCsvAndInsert(
                "csv/mahaller.csv",
                sql,
                2,
                (ps, cols) -> {
                    // CSV sırası: cols[0]=zone_name, cols[1]=sector
                    ps.setString(1, cols[0]); // zone_name
                    ps.setString(2, cols[1]); // sector
                }
        );
    }

    private void loadMahalCevrim() {
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM mahal_cevrim_t_hava");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot clear mahal_cevrim_t_hava", e);
        }
        String sql = "INSERT INTO mahal_cevrim_t_hava"
                + "(zone_name, per_person_rate, air_changes, pressure, multiplier) "
                + "VALUES(?,?,?,?,?)";
        loadCsvAndInsert("csv/mahal_cevrim_t_hava.csv", sql, 5, (ps, cols) -> {
            ps.setString(1, cols[0]);
            ps.setDouble(2, Double.parseDouble(cols[1]));
            ps.setDouble(3, Double.parseDouble(cols[2]));
            ps.setDouble(4, Double.parseDouble(cols[3]));
            ps.setDouble(5, Double.parseDouble(cols[4]));
        });
    }

    private void loadWinterConditions() {
        String sql = """
                MERGE INTO winter_conditions(city, kt) 
                KEY(city) VALUES(?,?)
                """;
        loadCsvAndInsert("csv/winter.csv", sql, 2, (ps, cols) -> {
            ps.setString(1, cols[0].trim());                       // city
            ps.setInt(2, Integer.parseInt(cols[1].trim()));     // kt
        });
    }



    private void loadEnthalpyTable() {
        String sql = """
        INSERT INTO enthalpy_table(
          dry_bulb_temp, wet_bulb_temp, fresh_air_ratio,
          mixed_enthalpy, return_air_flow, return_air_enthalpy,
          kt, yt, tmix_enthalpy, processing_enthalpy_15c,
          enthalpy_diff, col12
        ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
        """;
        loadCsvAndInsert("csv/ACS).csv", sql, 12, (ps, cols) -> {
            // parse first six as before
            for (int i = 0; i < 6; i++) {
                ps.setDouble(i+1, cols[i].isEmpty() ? 0.0 : Double.parseDouble(cols[i]));
            }
            // next two: kt, yt as ints (or 0 if blank)
            ps.setInt(7, cols[6].isEmpty() ? 0 : Integer.parseInt(cols[6]));
            ps.setInt(8, cols[7].isEmpty() ? 0 : Integer.parseInt(cols[7]));
            // tmix_enthalpy, processing_enthalpy_15c, enthalpy_diff, col12
            for (int i = 8; i < 12; i++) {
                ps.setDouble(i+1, cols[i].isEmpty() ? 0.0 : Double.parseDouble(cols[i]));
            }
        });
    }






    // Boş veya hatalı gelenleri sıfır kabul eden yardımcı metot
    private double parseOrZero(String s) {
        if (s == null || s.isBlank()) return 0.0;
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }









   /* private void loadDeviceSeriesModels() {
        String sql = "INSERT INTO device_series_models(series_name,model_number) VALUES(?,?)";
        loadCsvAndInsert("csv/device_series_models.csv", sql, 2, (ps, cols) -> {
            ps.setString(1, cols[0]);
            ps.setString(2, cols[1]);
        });
    }
    public void loadDeviceModels() {
        DeviceModelDAO dao = new DeviceModelDAO(db);
        for (DeviceModel dm : DeviceModel.values()) {
            dao.insertModel(dm.name());
        }
    } */

    /**
     * Generic CSV loader: reads resource, splits by comma, skips header, executes batch.
     * @param resourcePath path under resources
     * @param sql insert SQL with ? placeholders
     * @param columnCount expected columns per row
     * @param setter lambda to bind PreparedStatement parameters
     */
    private void loadCsvAndInsert(String resourcePath,
                                  String sql,
                                  int columnCount,
                                  ThrowingBiConsumer<PreparedStatement,String[]> setter) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Skip header
            reader.readLine();
            String line;
            int batchSize = 0;
            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(",", -1);
                if (cols.length != columnCount) continue;
                setter.accept(ps, cols);
                ps.addBatch();
                if (++batchSize % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load CSV: " + resourcePath, e);
        }
    }

    /**
     * Functional interface allowing SQLException in lambda
     */
    @FunctionalInterface
    interface ThrowingBiConsumer<T, U> {
        void accept(T t, U u) throws SQLException;
    }
}
