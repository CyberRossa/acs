package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.calculation.CalculationEngine;
import org.example.calculation.CalculationEngineImpl;
import org.example.calculation.CalculationResult;
import org.example.dao.CityDAO;
import org.example.dao.CountryDAO;
import org.example.dao.MahallDAO;
import org.example.dao.SectorDAO;
import org.example.services.DatabaseService;
import org.example.services.DriverManagerDatabaseService;
import org.example.model.CalculationInput;
import org.example.model.DeviceModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Controller for the calculation tab:
 * - binds all UI controls
 * - loads combobox data with filtering
 * - constructs CalculationInput
 * - triggers engine.calculate()
 * - populates resultTable
 */
public class CalculationController {
    // Project info
    @FXML private TextField projectNameField;
    @FXML private DatePicker projectDatePicker;
    @FXML private CheckBox manualAirChangesCheckBox;
    @FXML private TextField airChangesField;

    // Location & sector (filterable)
    @FXML private ComboBox<String> countryBox;
    @FXML private ComboBox<String> cityBox;
    @FXML private ComboBox<String> zoneBox;
  //  @FXML private ComboBox<String> sectorBox;

    // Dimensions
    @FXML private TextField widthField;
    @FXML private TextField lengthField;
    @FXML private TextField heightField;

    // Ventilation & occupancy

    @FXML private TextField peopleCountField;
    @FXML private TextField manualFreshAirField;

    // Equipment counts
    @FXML private TextField filter2Field;
    @FXML private TextField silencerField;
    @FXML private TextField filter3Field;
    @FXML private TextField heatRecoveryField;
    @FXML private TextField waterHeaterField;
    @FXML private TextField electricHeaterField;
    @FXML private TextField waterCoolerField;
    @FXML private TextField steamHumidifierField;
    @FXML private TextField aspiratorField;
    @FXML private TextField ventilatorField;

    // Design conditions
    @FXML private ComboBox<Integer> summerTempBox;
    @FXML private ComboBox<Integer> winterTempBox;

    // Additional options
    @FXML private CheckBox automationCheckBox;
    @FXML private ComboBox<DeviceModel> deviceModelBox;

    // Results table
    @FXML private TableView<CalculationResult> resultTable;
    @FXML private TableColumn<CalculationResult, Integer> colWinterBlow;
    @FXML private TableColumn<CalculationResult, Integer> colSummerBlow;
    @FXML private TableColumn<CalculationResult, Integer> colOutsideWinter;
    @FXML private TableColumn<CalculationResult, Integer> colOutsideSummerDry;
    @FXML private TableColumn<CalculationResult, Integer> colOutsideSummerWet;
    @FXML private TableColumn<CalculationResult, Integer> colFreshAir;
    @FXML private TableColumn<CalculationResult, Integer> colFreshRatio;
    @FXML private TableColumn<CalculationResult, Integer> colAspirator;
    @FXML private TableColumn<CalculationResult, Integer> colExhaust;
    @FXML private TableColumn<CalculationResult, Integer> colHeating;
    @FXML private TableColumn<CalculationResult, Integer> colCooling;
    @FXML private CheckBox manualFreshAirCheckBox;


    private CalculationEngine engine;
    private final ObservableList<String> allCountries = FXCollections.observableArrayList();
    private final ObservableList<String> allCities    = FXCollections.observableArrayList();
    private final ObservableList<String> allZones     = FXCollections.observableArrayList();
 //   private final ObservableList<String> allSectors   = FXCollections.observableArrayList();

    private final DatabaseService db =
            new DriverManagerDatabaseService("jdbc:h2:mem:acsdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL", "sa", "");


    @FXML
    public void initialize() throws SQLException {
        // 1) Setup DB & engine

        // 1) Engine ve static seed (bir kere)
        engine = new CalculationEngineImpl(db);
        new org.example.loader.StaticDataLoader(db).loadAll();
      //  manualFreshAirField.setDisable(true);
      //  manualFreshAirField.disableProperty()
    //            .bind(manualFreshAirCheckBox.selectedProperty().not());
    //    airChangesField.setDisable(true);
   //     airChangesField.disableProperty()
    //            .bind(manualAirChangesCheckBox.selectedProperty().not());

        try (Connection c = db.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT city, kt FROM winter_conditions")) {
            while (rs.next()) {
                System.out.printf("winter_conditions row: city='%s', kt=%d%n",
                        rs.getString("city"), rs.getInt("kt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2) Ülke listesini doldur
        allCountries.setAll(new CountryDAO(db).findAll());
        countryBox.setItems(allCountries);
        countryBox.setEditable(true);
        setupFiltering(countryBox, allCountries);

        // 3) Ülke seçilince şehirleri getir
        cityBox.setItems(allCities);
        cityBox.setEditable(true);
        countryBox.getSelectionModel().selectedItemProperty().addListener((o, oldC, newC) -> {
            allCities.setAll(new CityDAO(db).findByCountry(newC));
        });
        setupFiltering(cityBox, allCities);

        // 4) Mahaller sabit tüm listeyse bir kere yükle
        allZones.setAll(new MahallDAO(db).findAllZones());
        zoneBox.setItems(allZones);
        zoneBox.setEditable(true);
        setupFiltering(zoneBox, allZones);

        // 5) Sektörler
     //   allSectors.setAll(new SectorDAO(db).findAll());
     //   sectorBox.setItems(allSectors);
     //   sectorBox.setEditable(true);
     //   setupFiltering(sectorBox, allSectors);

        // 3) Populate numeric ComboBoxes
        summerTempBox.setItems(FXCollections.observableArrayList(21,22,23,24));
        winterTempBox.setItems(FXCollections.observableArrayList(20,21,22,23,24,25,26,27,28,29,30));

        // 4) Device models & defaults
        deviceModelBox.setItems(FXCollections.observableArrayList(DeviceModel.values()));
        deviceModelBox.getSelectionModel().selectFirst();
        automationCheckBox.setSelected(false);

        // 5) Configure result table columns
        colWinterBlow     .setCellValueFactory(new PropertyValueFactory<>("winterBlowTemp"));
        colSummerBlow     .setCellValueFactory(new PropertyValueFactory<>("summerBlowTemp"));
        colOutsideWinter  .setCellValueFactory(new PropertyValueFactory<>("outsideWinter"));
        colOutsideSummerDry.setCellValueFactory(new PropertyValueFactory<>("outsideSummerDry"));
        colOutsideSummerWet.setCellValueFactory(new PropertyValueFactory<>("outsideSummerWet"));
        colFreshAir       .setCellValueFactory(new PropertyValueFactory<>("freshAirAmount"));
        colFreshRatio     .setCellValueFactory(new PropertyValueFactory<>("freshAirRatio"));
        colAspirator      .setCellValueFactory(new PropertyValueFactory<>("aspiratorFlow"));
        colExhaust        .setCellValueFactory(new PropertyValueFactory<>("exhaustFlow"));
        colHeating        .setCellValueFactory(new PropertyValueFactory<>("heatingCapacity"));
        colCooling        .setCellValueFactory(new PropertyValueFactory<>("coolingCapacity"));
        resultTable.setItems(FXCollections.observableArrayList());
    }

    /** Enables typing+filtering in editable ComboBox */
    private <T> void setupFiltering(ComboBox<T> combo, ObservableList<T> base) {
        combo.getEditor().textProperty().addListener((obs, old, txt) -> {
            combo.setItems(base.filtered(item ->
                    item.toString().toLowerCase().contains(txt.toLowerCase())
            ));
            combo.show();
        });
    }

    @FXML
    private void onCalculate() {
        try {
            CalculationInput in = new CalculationInput();
            in.setProjectName(projectNameField.getText());
            in.setProjectDate(java.sql.Date.valueOf(projectDatePicker.getValue()));

            in.setCountry(countryBox.getValue());
            in.setCity(cityBox.getValue());
            in.setZone(zoneBox.getValue());
          //  in.setSector(sectorBox.getValue());
            in.setWidth(Integer.parseInt(widthField.getText()));
            in.setLength(Integer.parseInt(lengthField.getText()));
            in.setHeight(Integer.parseInt(heightField.getText()));

         /*   boolean manualAir = manualAirChangesCheckBox.isSelected();
            boolean manualFresh = manualFreshAirCheckBox.isSelected();

          if (!manualAir && !manualFresh) {
                in.setAirChangesPerHour(
                        Integer.parseInt(airChangesField.getPromptText()) // örn. default prompt’ta gösterilen değer
                );
                in.setManualFreshAirRatio(null);

            }
            // 2) Sadece “Manuel Taze Hava” seçili: otomatik hava çevrim + manuel taze oran
            else if (!manualAir && manualFresh) {
                in.setAirChangesPerHour(
                        Integer.parseInt(airChangesField.getPromptText())
                );
                String freshTxt = manualFreshAirField.getText().trim();
                if (freshTxt.isEmpty()) throw new IllegalArgumentException("Manuel taze hava oranı boş");
                in.setManualFreshAirRatio(Integer.parseInt(freshTxt));
            }
            // 3) “Manuel Hava Çevrim” ve “Manuel Taze Hava” birlikte seçili:
            else if (manualAir && manualFresh) {
                String airTxt = airChangesField.getText().trim();
                if (airTxt.isEmpty()) throw new IllegalArgumentException("Manuel hava çevrim boş");
                in.setAirChangesPerHour(Integer.parseInt(airTxt));

                String freshTxt = manualFreshAirField.getText().trim();
                if (freshTxt.isEmpty()) throw new IllegalArgumentException("Manuel taze hava oranı boş");
                in.setManualFreshAirRatio(Integer.parseInt(freshTxt));
            }


            in.setAirChangesPerHour(Integer.parseInt(airChangesField.getText()));
            in.setPeopleCount(Integer.parseInt(peopleCountField.getText()));
            in.setManualFreshAirRatio(
                    manualFreshAirField.getText().isEmpty()
                            ? null
                            : Integer.valueOf(manualFreshAirField.getText())
            );*/
            in.setFilter2Count    (Integer.parseInt(filter2Field.getText()));
            in.setSilencerCount   (Integer.parseInt(silencerField.getText()));
            in.setFilter3Count    (Integer.parseInt(filter3Field.getText()));
            in.setHeatRecoveryCount(Integer.parseInt(heatRecoveryField.getText()));
            in.setWaterHeaterCount (Integer.parseInt(waterHeaterField.getText()));
            in.setElectricHeaterCount(Integer.parseInt(electricHeaterField.getText()));
            in.setWaterCoolerCount(Integer.parseInt(waterCoolerField.getText()));
            in.setSteamHumidifierCount(Integer.parseInt(steamHumidifierField.getText()));
            in.setAspiratorCount  (Integer.parseInt(aspiratorField.getText()));
            in.setVentilatorCount (Integer.parseInt(ventilatorField.getText()));
            in.setSummerDesignTemp(summerTempBox.getValue());
            in.setWinterDesignTemp(winterTempBox.getValue());
            in.setAutomation(automationCheckBox.isSelected());
            in.setDeviceModel(deviceModelBox.getValue());

            // Perform calculation and show in table
            CalculationResult res = engine.calculate(in);
            resultTable.setItems(FXCollections.singletonObservableList(res));

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Hata: " + e.getMessage())
                    .showAndWait();


        }
    }
}


/*package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.calculation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.CityDAO;
import org.example.dao.CountryDAO;
import org.example.dao.MahallDAO;
import org.example.dao.SectorDAO;
import org.example.model.CalculationInput;
import org.example.model.CoolingSystem;
import org.example.model.DeviceModel;
import org.example.services.DriverManagerDatabaseService;


import java.time.LocalDate;
import java.util.Arrays;

public class CalculationController {

        @FXML private TextField projectNameField;
        @FXML private DatePicker projectDatePicker;
        @FXML private ComboBox<String> countryBox;
        @FXML private ComboBox<String> cityBox;
        @FXML private ComboBox<String> zoneBox;
        @FXML private ComboBox<String> sectorBox;
        @FXML private TextField widthField;
        @FXML private TextField lengthField;
        @FXML private TextField heightField;
        @FXML private TextField airChangesField;
        @FXML private TextField peopleCountField;
        @FXML private TextField manualFreshAirField;
        @FXML private TextField heatRecoveryField;
        @FXML private TextField waterHeaterField;
        @FXML private TextField waterCoolerField;
        @FXML private ComboBox<Integer> summerTempBox;
        @FXML private ComboBox<Integer> winterTempBox;
        @FXML private CheckBox automationCheckBox;
        @FXML private ComboBox<DeviceModel> deviceModelBox;
        @FXML private TableView<CalculationResult> resultTable;
        @FXML private TableColumn<CalculationResult, Double> colWinterBlow;
        @FXML private TableColumn<CalculationResult, Double> colSummerBlow;
        @FXML private TableColumn<CalculationResult, Double> colOutside;
        @FXML private TableColumn<CalculationResult, Double> colFreshAir;
        @FXML private TableColumn<CalculationResult, Integer> colFreshRatio;
        @FXML private TableColumn<CalculationResult, Double> colAspirator;
        @FXML private TableColumn<CalculationResult, Double> colExhaust;
        @FXML private TableColumn<CalculationResult, Double> colHeating;
        @FXML private TableColumn<CalculationResult, Double> colCooling;

        private CalculationEngine engine;
        private ObservableList<String> allCountries, allCities, allZones, allSectors;

        @FXML
        public void initialize() {
            var db = new DriverManagerDatabaseService("jdbc:h2:mem:acsdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL", "sa", "");
            engine = new CalculationEngineImpl(db);
            new org.example.loader.StaticDataLoader(db).loadAll();

            // Load static lists
            allCountries = FXCollections.observableArrayList(new CountryDAO(db).findAll());
            countryBox.setItems(allCountries); countryBox.setEditable(true);
            setupFiltering(countryBox, allCountries);

            allCities = FXCollections.observableArrayList(); cityBox.setEditable(true);
            countryBox.getSelectionModel().selectedItemProperty().addListener((obs, old, nc) -> {
                allCities.setAll(new CityDAO(db).findByCountry(nc)); cityBox.setItems(allCities);
            });
            setupFiltering(cityBox, allCities);

            allZones = FXCollections.observableArrayList(); zoneBox.setEditable(true);
            cityBox.getSelectionModel().selectedItemProperty().addListener((obs, old, city) -> {
                allZones.setAll(new MahallDAO(db).findByCity(city)); zoneBox.setItems(allZones);
            });
            setupFiltering(zoneBox, allZones);

            allSectors = FXCollections.observableArrayList(new SectorDAO(db).findAll());
            sectorBox.setItems(allSectors); sectorBox.setEditable(true);
            setupFiltering(sectorBox, allSectors);

            // Numeric comboboxes
            summerTempBox.setItems(FXCollections.observableArrayList(10,15,20,25,30));
            winterTempBox.setItems(FXCollections.observableArrayList(0,5,10,15,20));

            // Other controls
            deviceModelBox.getItems().addAll(DeviceModel.values());
            deviceModelBox.getSelectionModel().selectFirst();
            automationCheckBox.setSelected(false);

            // Table columns
            colWinterBlow.setCellValueFactory(new PropertyValueFactory<>("winterBlowTemp"));
            colSummerBlow.setCellValueFactory(new PropertyValueFactory<>("summerBlowTemp"));
            colOutside.setCellValueFactory(new PropertyValueFactory<>("outsideTemp"));
            colFreshAir.setCellValueFactory(new PropertyValueFactory<>("freshAirAmount"));
            colFreshRatio.setCellValueFactory(new PropertyValueFactory<>("freshAirRatio"));
            colAspirator.setCellValueFactory(new PropertyValueFactory<>("aspiratorFlow"));
            colExhaust.setCellValueFactory(new PropertyValueFactory<>("exhaustFlow"));
            colHeating.setCellValueFactory(new PropertyValueFactory<>("heatingCapacity"));
            colCooling.setCellValueFactory(new PropertyValueFactory<>("coolingCapacity"));
            resultTable.setItems(FXCollections.observableArrayList());
        }

        private <T> void setupFiltering(ComboBox<T> combo, ObservableList<T> data) {
            combo.getEditor().textProperty().addListener((obs, old, text) -> {
                ObservableList<T> filtered = data.filtered(item -> item.toString().toLowerCase().contains(text.toLowerCase()));
                combo.setItems(filtered);
                combo.show();
            });
        }

        @FXML
        private void onCalculate() {
            try {
                CalculationInput in = new CalculationInput();
                in.setProjectName(projectNameField.getText());
                in.setProjectDate(java.sql.Date.valueOf(projectDatePicker.getValue()));
                in.setCountry(countryBox.getValue());
                in.setCity(cityBox.getValue());
                in.setZone(zoneBox.getValue());
                in.setSector(sectorBox.getValue());
                in.setWidth(Integer.parseInt(widthField.getText()));
                in.setLength(Integer.parseInt(lengthField.getText()));
                in.setHeight(Integer.parseInt(heightField.getText()));
                in.setAirChangesPerHour(Integer.parseInt(airChangesField.getText()));
                in.setPeopleCount(Integer.parseInt(peopleCountField.getText()));
                in.setManualFreshAirRatio(
                        manualFreshAirField.getText().isEmpty() ? null : Integer.valueOf(manualFreshAirField.getText())
                );
                in.setHeatRecoveryCount(Integer.parseInt(heatRecoveryField.getText()));
                in.setWaterHeaterCount(Integer.parseInt(waterHeaterField.getText()));
                in.setWaterCoolerCount(Integer.parseInt(waterCoolerField.getText()));
                in.setSummerDesignTemp(summerTempBox.getValue());
                in.setWinterDesignTemp(winterTempBox.getValue());
                in.setAutomation(automationCheckBox.isSelected());
                in.setDeviceModel(deviceModelBox.getValue());

                CalculationResult res = engine.calculate(in);
                resultTable.setItems(FXCollections.singletonObservableList(res));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Hata: " + e.getMessage()).showAndWait();
            }
        }
    }*/