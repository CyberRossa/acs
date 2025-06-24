package org.example.services;

import org.example.calculation.CalculationEngine;
import org.example.controller.CalculationController;

public class GlobalServices {

    private static DatabaseService databaseService;
    private static LogService logService;
    private static UserService userService;
    private static ReportService reportService;
    private static IcmalService icmalService;
    private static ExportService exportService;
    private static PriceService priceService;
    private static CalculationEngine calculationEngine;
    private static LookupService lookupService;

    private GlobalServices() {
        // Singleton, new ile oluşturulmasın
    }

    public static CalculationEngine getCalculationEngine() {
        return calculationEngine;
    }

    public static void initialize() {
        databaseService = new DatabaseService();
        logService = new LogService(databaseService);
        userService = new UserService(databaseService);
        reportService = new ReportService(databaseService, logService);
        icmalService = new IcmalService(databaseService);
        exportService = new ExportService();
        priceService = new PriceService(databaseService, logService);
        lookupService = new LookupService(databaseService);
    }

    public static DatabaseService getDatabaseService() {
        return databaseService;
    }

    public static LogService getLogService() {
        return logService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static ReportService getReportService() {
        return reportService;
    }

    public static IcmalService getIcmalService() {
        return icmalService;
    }

    public static ExportService getExportService() {
        return exportService;
    }
    public static PriceService getPriceService() {
        return priceService;
    }

    public static LookupService getLookupService() {
        return lookupService;
    }
}

