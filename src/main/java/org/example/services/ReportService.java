package org.example.services;

import org.example.dao.ReportDAO;
import org.example.model.ACSPLUSInput;
import org.example.model.ACSPLUSOutput;
import org.example.model.ReportRecord;

import java.util.List;

public class ReportService {

    private final ReportDAO reportDAO;
    private final LogService logService;

    public ReportService(DatabaseService databaseService, LogService logService) {
        this.reportDAO = new ReportDAO(databaseService);
        this.logService = logService;
    }

    public void saveReport(String projectName, String clientName, ACSPLUSInput input, ACSPLUSOutput output) {
        boolean success = reportDAO.insertReport(projectName, clientName, input, output);
        if (success) {
            logService.log("INFO", "Yeni rapor kaydedildi: " + projectName + " - " + clientName);
        } else {
            logService.log("ERROR", "Rapor kaydedilirken hata oluştu: " + projectName + " - " + clientName);
        }
    }

    public List<ReportRecord> getAllReports() {
        return reportDAO.getAllReports();
    }

    public void clearAll() {
        if (reportDAO.clearAllReports()) {
            logService.log("INFO", "Tüm raporlar temizlendi.");
        } else {
            logService.log("ERROR", "Tüm raporlar temizlenirken hata oluştu.");
        }
    }
}
