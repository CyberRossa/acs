package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.example.model.ReportRecord;
import org.example.services.ReportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.services.GlobalServices;

import java.io.IOException;
import java.util.List;

public class ReportListController {
    @FXML private TableView<ReportRecord> reportTable;
    @FXML private TableColumn<ReportRecord, String> idCol;
    @FXML private TableColumn<ReportRecord, String> projectCol;
    @FXML private TableColumn<ReportRecord, String> clientCol;
    @FXML private TableColumn<ReportRecord, String> dateCol;

    @FXML private Button viewButton;
    @FXML private Button deleteButton;
    @FXML private Button refreshButton;

    @FXML private StackPane reportDetailPane;

    private final ReportService reportService = GlobalServices.getReportService();
    private final ObservableList<ReportRecord> reports = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        idCol.setCellValueFactory(data -> data.getValue().projectNameProperty());
        projectCol.setCellValueFactory(data -> data.getValue().clientNameProperty());
        clientCol.setCellValueFactory(data -> data.getValue().createdDateProperty().asString());

        dateCol.setCellValueFactory(cd -> cd.getValue().createdDateProperty().asString());

        refreshTable();

        viewButton.setOnAction(e -> openSelectedReport());
        deleteButton.setOnAction(e -> {
            ReportRecord sel = reportTable.getSelectionModel().getSelectedItem();
            if (sel != null) {
                reportService.clearAll();  // veya reportService.deleteReport(sel.getReportId());
                refreshTable();
            }
        });
        refreshButton.setOnAction(e -> refreshTable());

    }

    private void refreshTable() {
        List<ReportRecord> list = reportService.getAllReports();
        reports.setAll(list);
        reportTable.setItems(reports);
    }


    private void openSelectedReport() {
        ReportRecord selected = reportTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Report.fxml"));
                Node reportPage = loader.load();

                ReportController controller = loader.getController();
                controller.setReportData(selected.getProjectName(), selected.getOutput());

                reportDetailPane.getChildren().setAll(reportPage);
                // contentArea.getChildren().setAll(reportPage);
                // İçerik alanına yüklendiği varsayımı
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
