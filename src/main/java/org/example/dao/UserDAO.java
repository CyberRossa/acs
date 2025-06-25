package org.example.dao;

import org.example.model.User;
import org.example.services.DatabaseService;
import java.sql.*;

import java.sql.SQLException;
import java.util.Optional;

public class UserDAO {
    private final DatabaseService db;

    public UserDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
              username VARCHAR(50) PRIMARY KEY,
              password_hash VARCHAR(100) NOT NULL,
              role VARCHAR(20) NOT NULL
            )
            """;
        try (Connection c = db.getConnection();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("users tablosu oluşturulamadı", e);
        }
    }

    /** Yeni kullanıcı ekler (hash zaten hesaplanmış olmalı) */
    public void insert(User u) {
        String sql = "MERGE INTO users(username,password_hash,role) KEY(username) VALUES(?,?,?)";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("users tablosuna ekleme başarısız", e);
        }
    }

    /** Kullanıcı adıyla arama yapar */
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT username,password_hash,role FROM users WHERE username = ?";
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUsername( rs.getString("username") );
                    u.setPasswordHash( rs.getString("password_hash") );
                    u.setRole( rs.getString("role") );
                    return Optional.of(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("users sorgulama hatası", e);
        }
        return Optional.empty();
    }
}

/*public class UserDAO {
    private final DatabaseService db;
    public UserDAO(DatabaseService db) {
        this.db = db;
        createTableIfNotExists();
    }
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "username VARCHAR(50) PRIMARY KEY,"
                + "password VARCHAR(50),"
                + "role VARCHAR(20)"
                + ")";
        try (var c = db.getConnection(); var s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ensureDefaultAdmin() {
        String check = "SELECT COUNT(*) FROM users WHERE username='admin'";
        String insert = "INSERT INTO users (username, password, role) VALUES ('admin','admin','admin')";
        try (var c = db.getConnection(); var stmt = c.createStatement()) {
            var rs = stmt.executeQuery(check);
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute(insert);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByUsername(String uname) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (var c = db.getConnection(); var ps = c.prepareStatement(sql)) {
            ps.setString(1, uname);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}*/