package org.example.model;

public class WinterDesignCondition {
    private String cityName;
    private double wetBulbTemp;
    private double dryBulbTemp;

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public double getWetBulbTemp() { return wetBulbTemp; }
    public void setWetBulbTemp(double wetBulbTemp) { this.wetBulbTemp = wetBulbTemp; }

    public double getDryBulbTemp() { return dryBulbTemp; }
    public void setDryBulbTemp(double dryBulbTemp) { this.dryBulbTemp = dryBulbTemp; }
}
