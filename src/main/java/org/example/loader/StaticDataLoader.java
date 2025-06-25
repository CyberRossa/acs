package org.example.loader;

import org.example.dao.*;
import org.example.model.User;
import org.example.services.DatabaseService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Objects;

public class StaticDataLoader {
    private final DatabaseService db;

    public StaticDataLoader(DatabaseService db) {
        this.db = db;
    }

    /** Tüm CSV’leri sırayla yükler */
    public void loadAll() throws SQLException {
        // DAOlara sadece tablo oluşturma işlemi için örnekler
        new CountryDAO(db);
        new CityDAO(db);
        new MahallDAO(db);
        new SectorDAO(db);
        new MahallCevrimDAO(db);
        new EnthalpyTableDAO(db);
        new WinterDesignConditionsDAO(db);
        new SummerDesignConditionsDAO(db);
        new UserDAO(db);

        // Örnek admin kullanıcısı
        UserDAO udao = new UserDAO(db);
        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
        admin.setRole("ADMIN");
        udao.insert(admin);

        // CSV’lerden veri yükleme
        loadCountries();
        loadCities();
        loadMahaller();
        loadMahalCevrim();
        loadSummerConditions();
        loadWinterConditions();
        loadEnthalpyTable();
    }

    private void loadCountries() {
        String sql = "MERGE INTO countries(country_name) KEY(country_name) VALUES(?)";
        loadCsvAndInsert("csv/countries.csv", 1, sql, (ps, cols) -> {
            ps.setString(1, cols[0].trim());
        });
    }

    private void loadCities() {
        String sql = "MERGE INTO cities(name,country) KEY(name,country) VALUES(?,?)";
        loadCsvAndInsert("csv/cities.csv", 2, sql, (ps, cols) -> {
            ps.setString(1, cols[1].trim());
            ps.setString(2, cols[0].trim());
        });
    }

    private void loadMahaller() {
        String sql = "MERGE INTO mahaller(zone_name, sector) KEY(zone_name) VALUES(?,?)";
        loadCsvAndInsert("csv/mahaller.csv", 2, sql, (ps, cols) -> {
            ps.setString(1, cols[0].trim());
            ps.setString(2, cols[1].trim());
        });
    }

    private void loadMahalCevrim() {
        String sql = """
            MERGE INTO mahal_cevrim_t_hava
            (zone_name, per_person_rate, air_changes, pressure, multiplier)
            KEY(zone_name)
            VALUES(?,?,?,?,?)
            """;
        loadCsvAndInsert("csv/mahal_cevrim_t_hava.csv", 5, sql, (ps, cols) -> {
            ps.setString(1, cols[0].trim());
            ps.setDouble(2, parseOrZero(cols[1]));
            ps.setDouble(3, parseOrZero(cols[2]));
            ps.setDouble(4, parseOrZero(cols[3]));
            ps.setDouble(5, parseOrZero(cols[4]));
        });
    }

    private void loadSummerConditions() {
        String sql = """
            MERGE INTO summer_conditions(city, kt, yt, kt_yt_code)
            KEY(city)
            VALUES(?,?,?,?)
            """;
        loadCsvAndInsert("csv/summer.csv", 4, sql, (ps, cols) -> {
            ps.setString(1, cols[0].trim());
            ps.setInt   (2, (int) parseOrZero(cols[1]));
            ps.setInt   (3, (int) parseOrZero(cols[2]));
            ps.setString(4, cols[3].trim());
        });
    }

    private void loadWinterConditions() {
        String sql = """
            MERGE INTO winter_conditions(city, kt)
            KEY(city)
            VALUES(?,?)
            """;
        loadCsvAndInsert("csv/winter.csv", 2, sql, (ps, cols) -> {
            ps.setString(1, cols[0].trim());
            ps.setInt   (2, (int) parseOrZero(cols[1]));
        });
    }
    private void loadEnthalpyTable() {
        // match the 12 columns you showed: KT,YT,TAZE HAVA ORANI,concate,...
        String sql = """
      MERGE INTO enthalpy_table(
        kt, yt, fresh_air_ratio, code,
        taze_hava, return_air, return_air_enthalpy,
        kt2, yt2, tmix_enthalpy,
        processing_enthalpy_15c, enthalpy_diff
      ) KEY(code) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
    """;
        loadCsvAndInsert("csv/enthalpy.csv", 12, sql, (ps, cols) -> {
            // 1-3: KT, YT, TAZE HAVA ORANI
            ps.setDouble(1, parseOrZero(cols[0]));
            ps.setDouble(2, parseOrZero(cols[1]));
            ps.setDouble(3, parseOrZero(cols[2]));
            // 4: concate (your lookup key)
            ps.setString(4, cols[3].trim());
            // 5-7: TAZE HAVA, DÖNÜŞ HAVA, DÖNÜŞ HAVA ENTHALPY
            ps.setDouble(5, parseOrZero(cols[4]));
            ps.setDouble(6, parseOrZero(cols[5]));
            ps.setDouble(7, parseOrZero(cols[6]));
            // 8-9: KT2, YT2
            ps.setDouble(8, parseOrZero(cols[7]));
            ps.setDouble(9, parseOrZero(cols[8]));
            // 10-12: Tmix Enthalpy, İşleme Enthalpy 15 C, enthalpy_diff
            ps.setDouble(10, parseOrZero(cols[9]));
            ps.setDouble(11, parseOrZero(cols[10]));
            ps.setDouble(12, parseOrZero(cols[11]));
        });
    }


    /** Helper: boş, hatalı veya “%” işaretli sayıları 0.0 olarak döndürür */
// in StaticDataLoader
    private double parseOrZero(String s) {
        if (s == null || s.isBlank()) return 0.0;
        try { return Double.parseDouble(s.trim()); }
        catch (NumberFormatException e) { return 0.0; }
    }


    /**
     * Generic CSV loader: UTF-8 okur, başlığı atlar, satır satır split ve batch
     */
    private void loadCsvAndInsert(String resourcePath,
                                  int expectedCols,
                                  String sql,
                                  ThrowingBiConsumer<PreparedStatement,String[]> binder) {
        try (
                InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8));
                Connection conn = db.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // başlığı atla
            reader.readLine();
            String line; int batch = 0;
            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(",", -1);
                if (cols.length != expectedCols) continue;
                // binder içinde PK=0 satırlarını atlama olabilir
                binder.accept(ps, cols);
                // binder atladıysa hiçbir şey bind’lenmemiştir
                if (ps.getParameterMetaData().getParameterCount() > 0) {
                    ps.addBatch();
                    if (++batch % 500 == 0) ps.executeBatch();
                }
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException("CSV yükleme hatası: " + resourcePath, e);
        }
    }

    @FunctionalInterface
    private interface ThrowingBiConsumer<T,U> {
        void accept(T t, U u) throws SQLException;
    }
}
