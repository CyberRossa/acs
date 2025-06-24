package org.example.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.LogEntry;
import org.example.services.GlobalServices;
import org.example.services.LogService;

    public class LogController {

        @FXML
        private TableView<LogEntry> logTable;

        @FXML
        private TableColumn<LogEntry, String> dateColumn;

        @FXML
        private TableColumn<LogEntry, String> levelColumn;

        @FXML
        private TableColumn<LogEntry, String> messageColumn;

        @FXML
        private Button clearLogsButton;

        private final LogService logService = GlobalServices.getLogService();
        private final ObservableList<LogEntry> logs = FXCollections.observableArrayList();

        @FXML
        public void initialize() {
            // Tablo kolonlarını bağlama
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            levelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
            messageColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

            refreshLogTable();

            clearLogsButton.setOnAction(event -> {
                logService.clearLogs();
                refreshLogTable();
            });
        }

        private void refreshLogTable() {
            logs.setAll(logService.getAllLogs());
            logTable.setItems(logs);
        }
    }
