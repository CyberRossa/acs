package org.example.services;

import org.example.dao.LogDAO;
import org.example.model.LogEntry;

import java.time.LocalDateTime;
import java.util.List;

public class LogService {

    private final LogDAO logDAO;

    public LogService(DatabaseService databaseService) {
        this.logDAO = new LogDAO(databaseService);
    }

    public void log(String level, String message) {
        String date = LocalDateTime.now().toString();
        boolean success = logDAO.insertLog(date, level, message);
        if (!success) {
            System.err.println("Log kaydı eklenemedi: " + message);
        }
    }

    public List<LogEntry> getAllLogs() {
        return logDAO.findAll();
    }

    public void clearLogs() {
        boolean success = logDAO.clearAll();
        if (!success) {
            System.err.println("Log kayıtları temizlenemedi.");
        }
    }
}
