package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.example.model.IcmalRow;
import org.example.services.*;
import org.example.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
public class IcmalController {

    @FXML private Label projectNameLabel;
    @FXML private Label clientNameLabel;

    @FXML private TableView<IcmalRow> icmalTable;
    @FXML private TableColumn<IcmalRow, String> deviceColumn;
    @FXML private TableColumn<IcmalRow, Double> capacityColumn;
    @FXML private TableColumn<IcmalRow, Double> airflowColumn;
    @FXML private TableColumn<IcmalRow, Double> powerColumn;
    @FXML private TableColumn<IcmalRow, Double> priceColumn;
    @FXML private TableColumn<IcmalRow, Double> totalColumn;

    @FXML private Button exportButton;
    @FXML private Button saveReportButton;
    @FXML private Button genelIcmalButton;

    private  final IcmalService icmalService = GlobalServices.getIcmalService();
    private  final ExportService exportService = GlobalServices.getExportService();
    private final ReportService reportService   = GlobalServices.getReportService();

    private final ObservableList<IcmalRow> rows = FXCollections.observableArrayList();


    private ACSPLUSInput input;
    private ACSPLUSOutput output;

    @FXML private StackPane detailPane;
// …

    @FXML
    private void initialize() {
        deviceColumn.setCellValueFactory(cellData -> cellData.getValue().deviceProperty());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());
        airflowColumn.setCellValueFactory(cellData -> cellData.getValue().airflowProperty().asObject());
        powerColumn.setCellValueFactory(cellData -> cellData.getValue().powerProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        exportButton.setOnAction(e -> export());
        saveReportButton.setOnAction(e -> saveReport());
        genelIcmalButton.setOnAction(e -> openGenelIcmal());


    }

    public void setIcmalData(ACSPLUSInput input, ACSPLUSOutput output) {
        projectNameLabel.setText(input.projectName);
        clientNameLabel.setText(input.clientName);
        List<IcmalRow> list = icmalService.generateIcmalRows(input, output);
        rows.setAll(list);
        icmalTable.setItems(rows);
    }

    private void export() {
        if (input == null || output == null) {
            showAlert(Alert.AlertType.WARNING, "Export Hatası", "Önce hesaplama yapılmalı.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Dosya Ayarla");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"),
                new FileChooser.ExtensionFilter("Excel (*.xls, *.xlsx)", "*.xls", "*.xlsx")
        );
        File file = chooser.showSaveDialog(exportButton.getScene().getWindow());
        if (file == null) return;

        String name = file.getAbsolutePath();
        String ext  = getExtension(name).toLowerCase();
        try {
            if (ext.equals("pdf")) {
                exportService.exportReportToPDF(name, input, output, rows);
            } else {
                exportService.exportReportToExcel(name, input, output, rows);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Export Hatası", "Dosya dışa aktarılırken hata oluştu.");
        }
    }

    private String getFileExtension(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx > 0) {
            return filename.substring(idx + 1);
        }
        return "";
    }

    private void saveReport() {
        if (input == null || output == null) {
            showAlert(Alert.AlertType.WARNING, "Kaydetme Hatası", "Önce hesaplama yapılmalı.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rapor Kaydet");
        dialog.setHeaderText("Müşteri Adını Giriniz:");
        dialog.setContentText("Müşteri:");

        dialog.showAndWait().ifPresent(clientName -> {
            if (clientName.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Müşteri ismi boş olamaz!");
            } else {
                reportService.saveReport(input.projectName, clientName.trim(), input, output);
                showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Rapor başarıyla kaydedildi.");
            }
        });
    }

    private void openGenelIcmal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GenelIcmal.fxml"));
            Node genelIcmalPage = loader.load();

            GenelIcmalController controller = loader.getController();
            controller.setData(rows);

            // İçerik alanına yükle (varsayalım bir content pane var)
            // contentArea.getChildren().setAll(genelIcmalPage);
            detailPane.getChildren().setAll(genelIcmalPage);


        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Hata", "Genel İcmal sayfası açılamadı.");
        }
    }

    private String getExtension(String filename) {
        int idx = filename.lastIndexOf('.');
        return (idx > 0 ? filename.substring(idx + 1) : "");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }


}