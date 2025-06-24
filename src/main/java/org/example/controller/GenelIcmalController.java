package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.model.IcmalRow;

import java.util.List;
public class GenelIcmalController {
    @FXML private Label totalAhuLabel;
    @FXML private Label totalCondensingLabel;
    @FXML private Label totalPanoLabel;
    @FXML private Label genelToplamLabel;

    public void setData(List<IcmalRow> icmalRows) {
        double totalAhu = 0, totalCondensing = 0, totalPano = 0;

        for (IcmalRow row : icmalRows) {
            if ("AHU".equalsIgnoreCase(row.getDevice())) totalAhu += row.getTotal();
            else if ("Condensing Unit".equalsIgnoreCase(row.getDevice())) totalCondensing += row.getTotal();
            else if ("Pano".equalsIgnoreCase(row.getDevice())) totalPano += row.getTotal();
        }

        totalAhuLabel.setText(String.format("%.2f", totalAhu));
        totalCondensingLabel.setText(String.format("%.2f", totalCondensing));
        totalPanoLabel.setText(String.format("%.2f", totalPano));
        genelToplamLabel.setText(String.format("%.2f", totalAhu + totalCondensing + totalPano));
    }
}