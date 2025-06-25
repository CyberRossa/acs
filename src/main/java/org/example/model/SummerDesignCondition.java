package org.example.model;

public class SummerDesignCondition {
    private String cityName;
    private double dryBulbTemp;
    private double wetBulbTemp;
    private double enthalpyDry;
    private double enthalpyWet;

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public double getDryBulbTemp() { return dryBulbTemp; }
    public void setDryBulbTemp(double dryBulbTemp) { this.dryBulbTemp = dryBulbTemp; }

    public double getWetBulbTemp() { return wetBulbTemp; }
    public void setWetBulbTemp(double wetBulbTemp) { this.wetBulbTemp = wetBulbTemp; }

    public double getEnthalpyDry() { return enthalpyDry; }
    public void setEnthalpyDry(double enthalpyDry) { this.enthalpyDry = enthalpyDry; }

    public double getEnthalpyWet() { return enthalpyWet; }
    public void setEnthalpyWet(double enthalpyWet) { this.enthalpyWet = enthalpyWet; }
}
