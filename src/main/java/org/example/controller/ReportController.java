package org.example.controller;

import org.example.model.ACSPLUSOutput;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class ReportController {

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label mahalNameLabel;

    @FXML
    private Label enthalpyLabel;

    @FXML
    private Label heatingLoadLabel;

    @FXML
    private Label coolingLoadLabel;

    @FXML
    private Label automationPowerLabel;

    public void setReportData(String projectName, ACSPLUSOutput output) {
        projectNameLabel.setText(projectName);
        mahalNameLabel.setText(output.mahalName);
        enthalpyLabel.setText(String.format("%.2f", output.enthalpy));
        heatingLoadLabel.setText(String.format("%.2f", output.heatingLoad));
        coolingLoadLabel.setText(String.format("%.2f", output.coolingLoad));
        automationPowerLabel.setText(String.format("%.2f", output.automationPower));
    }
}
