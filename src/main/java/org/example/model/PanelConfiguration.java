package org.example.model;

public class PanelConfiguration {
    private double motorPowerKw;
    private String startingMethod;
    private String contactorModel;
    private int contactorCount;
    private String starContactorModel;
    private String mpsModel;
    private String mpsSettingRange;
    private double usdExchangeRate;
    private int motorQuantity;
    private double panelPrice;

    public double getMotorPowerKw() { return motorPowerKw; }
    public void setMotorPowerKw(double motorPowerKw) { this.motorPowerKw = motorPowerKw; }

    public String getStartingMethod() { return startingMethod; }
    public void setStartingMethod(String startingMethod) { this.startingMethod = startingMethod; }

    public String getContactorModel() { return contactorModel; }
    public void setContactorModel(String contactorModel) { this.contactorModel = contactorModel; }

    public int getContactorCount() { return contactorCount; }
    public void setContactorCount(int contactorCount) { this.contactorCount = contactorCount; }

    public String getStarContactorModel() { return starContactorModel; }
    public void setStarContactorModel(String starContactorModel) { this.starContactorModel = starContactorModel; }

    public String getMpsModel() { return mpsModel; }
    public void setMpsModel(String mpsModel) { this.mpsModel = mpsModel; }

    public String getMpsSettingRange() { return mpsSettingRange; }
    public void setMpsSettingRange(String mpsSettingRange) { this.mpsSettingRange = mpsSettingRange; }

    public double getUsdExchangeRate() { return usdExchangeRate; }
    public void setUsdExchangeRate(double usdExchangeRate) { this.usdExchangeRate = usdExchangeRate; }

    public int getMotorQuantity() { return motorQuantity; }
    public void setMotorQuantity(int motorQuantity) { this.motorQuantity = motorQuantity; }

    public double getPanelPrice() { return panelPrice; }
    public void setPanelPrice(double panelPrice) { this.panelPrice = panelPrice; }
}
