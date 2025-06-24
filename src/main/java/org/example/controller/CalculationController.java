package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.calculation.*;
import org.example.model.*;
import org.example.services.GlobalServices;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.services.LookupService;

public class CalculationController {

    @FXML private TextField projectField;
    @FXML private TextField clientField;

    @FXML private ComboBox<String> countryBox;
    @FXML private ComboBox<String> cityBox;
    @FXML private ComboBox<String> mahalBox;

    @FXML private TextField widthField, depthField, heightField;
    @FXML private TextField personField, summerField, winterField;
    @FXML private TextField totalAirField, freshAirPctField, freshAirVolField;
    @FXML private TextField summerSupplyField, winterSupplyField;
    @FXML private TextField internalTempField;

    @FXML private ComboBox<String> filter2Box;
    @FXML private CheckBox silencerCheck, manualFreshCheck;
    @FXML private TextField exhaustField;
    @FXML private ComboBox<String> filter3Box, modelBox, freshDeviceBox;
    @FXML private TextField totalLoadField;

    @FXML private Button calculateButton;

    private final CalculationEngine engine       = GlobalServices.getCalculationEngine();
    private final LookupService lookupService    = GlobalServices.getLookupService();

    @FXML
    public void initialize() {
        // Dropdown’ları doldur

    }


}