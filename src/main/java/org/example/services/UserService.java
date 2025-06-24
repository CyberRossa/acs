package org.example.services;

import org.example.dao.UserDAO;
import org.example.model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService(DatabaseService databaseService) {
        this.userDAO = new UserDAO(databaseService);
    }
    public boolean addUser(String username, String password, String role) {
        UserData user = new UserData();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return userDAO.insert(user);
    }

    public UserData getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public boolean updateUserPassword(String username, String newPassword) {
        return userDAO.updatePassword(username, newPassword);
    }

    public boolean deleteUser(String username) {
        return userDAO.delete(username);
    }

    public List<UserData> getAllUsers() {
        return userDAO.findAll();
    }

    public boolean validatePassword(UserData user, String password) {
        // Burada basit düz metin karşılaştırma var. Gerçek uygulamada hash kullanılmalı.
        return user != null && user.getPassword().equals(password);
    }
}

 /*   public void createUserTable() {
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
            e.printStackTrace();
        }
    }

    public boolean insertUser(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role) VALUES(?, ?, ?)";

        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserData getUserByUsername(String username) {
        String sql = "SELECT username, password, role FROM users WHERE username = ?";
        UserData user = null;

        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserData();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean updateUserPassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = databaseService.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/