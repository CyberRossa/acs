package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.model.PriceData;
import org.example.services.GlobalServices;
import org.example.services.PriceService;
public class PriceController {

    @FXML private TextField ahuPriceField;
    @FXML private TextField condensingPriceField;
    @FXML private TextField panoPriceField;
    @FXML private Button updateButton;
    @FXML private Label statusLabel;

    private final PriceService priceService = GlobalServices.getPriceService();

    @FXML
    private void initialize() {
        loadPrices();
        updateButton.setOnAction(e -> updatePrices());
    }
    private void loadPrices() {
        PriceData pd = priceService.getCurrentPrices();
        ahuPriceField.setText(String.valueOf(pd.ahuPrice));
        condensingPriceField.setText(String.valueOf(pd.condensingPrice));
        panoPriceField.setText(String.valueOf(pd.panoPrice));
    }


    private void updatePrices() {
        try {
            double ahu = Double.parseDouble(ahuPriceField.getText());
            double cond = Double.parseDouble(condensingPriceField.getText());
            double pano = Double.parseDouble(panoPriceField.getText());

            priceService.updatePrices(ahu, cond, pano);
            statusLabel.setText("Fiyatlar güncellendi.");
            loadPrices();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Geçerli sayılar giriniz!");
        }
    }
}
