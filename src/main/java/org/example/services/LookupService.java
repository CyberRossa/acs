package org.example.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.dao.*;
import org.example.model.MahalParameters;

public class LookupService {
    private final CountryDAO countryDAO;
    private final CityDAO    cityDAO;
    private final MahalDAO   mahalDAO;
    private final ModelDAO   modelDAO;


    public LookupService(DatabaseService db) {
        this.countryDAO = new CountryDAO(db);
        this.cityDAO    = new CityDAO(db);
        this.mahalDAO   = new MahalDAO(db);
        this.modelDAO   = new ModelDAO(db);
    }

    public MahalParameters getMahalParameters(String code) {
        return mahalDAO.findParameters(code);
    }

    /** Ülke kodlarını döner (ComboBox<String>) */
    public ObservableList<String> getCountries() {
        return FXCollections.observableArrayList(countryDAO.findAllCodes());
    }

    /** Seçilen ülkenin şehirlerini döner */
    public ObservableList<String> getCities(String countryCode) {
        return FXCollections.observableArrayList(cityDAO.findByCountry(countryCode));
    }

    /** Mahal kodlarını döner */
    public ObservableList<String> getMahaller() {
        return FXCollections.observableArrayList(mahalDAO.findAllCodes());
    }

    /** “2. Filtre” seçeneklerini döner
    public ObservableList<String> getFilter2Options() {
        return FXCollections.observableArrayList(modelDAO.findDistinctFilter2());
    }*/

    /** “3. Filtre” seçeneklerini döner
    public ObservableList<String> getFilter3Options() {
        return FXCollections.observableArrayList(modelDAO.findDistinctFilter3());
    }*/

    /** Cihaz modellerini döner */
    public ObservableList<String> getModels() {
        return FXCollections.observableArrayList(modelDAO.findAllModels());
    }

    /** Taze hava cihazı seçeneklerini döner
    public ObservableList<String> getFreshDeviceOptions() {
        return FXCollections.observableArrayList(modelDAO.findDistinctFreshDevice());*/
    }


