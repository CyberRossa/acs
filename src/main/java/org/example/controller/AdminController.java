package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AdminController {
    @FXML
    private ListView<String> reportsList;

    @FXML
    public void initialize() {
        // Load report list from DB or file storage
        reportsList.getItems().addAll("Rapor1.pdf", "Rapor2.pdf");
    }

    @FXML
    private void onOpenReport() {
        String selected = reportsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Open report logic (e.g., launch PDF viewer)
            System.out.println("Opening report: " + selected);
        }
    }
}
