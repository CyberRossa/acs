package org.example.dao;

import com.google.gson.Gson;
import org.example.model.ACSPLUSInput;
import org.example.model.ACSPLUSOutput;
import org.example.model.ReportRecord;
import org.example.services.DatabaseService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportDAO {

    private final DatabaseService databaseService;
    private final Gson gson = new Gson();

    public ReportDAO(DatabaseService databaseService) {
        this.databaseService = databaseService;
        createTableIfNotExist();
    }

    private void createTableIfNotExist() {
        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS reports (" +
                    "reportId TEXT PRIMARY KEY," +
                    "projectName TEXT," +
                    "clientName TEXT," +
                    "inputJson TEXT," +
                    "outputJson TEXT," +
                    "createdDate TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Reports tablosu oluşturulamadı.", e);
        }
    }

    public boolean insertReport(String projectName, String clientName, ACSPLUSInput input, ACSPLUSOutput output) {
        String id = UUID.randomUUID().toString();
        String inJ = gson.toJson(input);
        String outJ = gson.toJson(output);
        String date = LocalDateTime.now().toString();

        String sql = "INSERT INTO reports (reportId, projectName, clientName, inputJson, outputJson, createdDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, projectName);
            ps.setString(3, clientName);
            ps.setString(4, inJ);
            ps.setString(5, outJ);
            ps.setString(6, date);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ReportRecord> getAllReports() {
        List<ReportRecord> reports = new ArrayList<>();

        String sql = "SELECT * FROM reports ORDER BY createdDate DESC";

        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String reportId = rs.getString("reportId");
                String projectName = rs.getString("projectName");
                String clientName = rs.getString("clientName");
                ACSPLUSInput input = gson.fromJson(rs.getString("inputJson"), ACSPLUSInput.class);
                ACSPLUSOutput output = gson.fromJson(rs.getString("outputJson"), ACSPLUSOutput.class);
                LocalDateTime createdDate = LocalDateTime.parse(rs.getString("createdDate"));

                reports.add(new ReportRecord(reportId, projectName, clientName, input, output, createdDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    public boolean clearAllReports() {
        String sql = "DELETE FROM reports";

        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
