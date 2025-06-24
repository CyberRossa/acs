package org.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.nio.file.*;

public class DatabaseService {


    private final String dbUrl;


    public DatabaseService() {

        String userHome = System.getProperty("user.home");

        // 2) Gizli .acsplus klasörü
        Path appDir = Paths.get(userHome, ".acsplus");

        try {
            if (Files.notExists(appDir)) {
                Files.createDirectories(appDir);
            }
        } catch (Exception e) {
            System.out.println("Uygulama dizini oluşturulamadı: " + appDir);
            throw new RuntimeException("Uygulama dizini oluşturulamadı: " + appDir, e);
        }
        // 3) Veritabanı dosyası yolu
        Path dbFile = appDir.resolve("acsplus.db");
        dbUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath();

        // 4) JDBC driver’ı yükle (gerekirse)
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver bulunamadı.", e);
        }

        // 5) İlk bağlantı test edip dosyayı yarat
        System.out.println("DB path → " + dbUrl);
        initializeDatabase();
        System.out.println("DB path → " + dbUrl);
    }

    private void initializeDatabase() {
        try (Connection conn = getConnection()) {

            if (conn == null) {
                throw new SQLException("Veritabanı bağlantısı oluşturulamadı.");
            }
                Statement stmt = conn.createStatement();

                // User tablosu
                stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                        "username TEXT PRIMARY KEY," +
                        "password TEXT NOT NULL," +
                        "role TEXT NOT NULL)");

                // Price tablosu
                stmt.execute("CREATE TABLE IF NOT EXISTS prices (" +
                        "id INTEGER PRIMARY KEY," +
                        "ahu REAL," +
                        "condensing REAL," +
                        "pano REAL)");

                // Report tablosu (şimdilik boş bırakıyoruz, ileride eklenecek)

                stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanı başlatılamadı.", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }
}