package org.example.dao;

import org.example.model.UserData;
import org.example.services.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final DatabaseService databaseService;

    public UserDAO(DatabaseService databaseService) {
        this.databaseService = databaseService;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            );
        """;
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("User tablosu oluşturulamadı.", e);
        }
    }
        public boolean insert(UserData user) {
            String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
            try (Connection conn = databaseService.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public UserData findByUsername(String username) {
            String sql = "SELECT username, password, role FROM users WHERE username = ?";
            try (Connection conn = databaseService.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        UserData u = new UserData();
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        return u;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public List<UserData> findAll() {
            List<UserData> list = new ArrayList<>();
            String sql = "SELECT username, password, role FROM users";
            try (Connection conn = databaseService.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    UserData u = new UserData();
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    list.add(u);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

        public boolean updatePassword(String username, String newPassword) {
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (Connection conn = databaseService.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPassword);
                ps.setString(2, username);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean delete(String username) {
            String sql = "DELETE FROM users WHERE username = ?";
            try (Connection conn = databaseService.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }




}