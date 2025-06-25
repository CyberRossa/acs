package org.example.model;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CalculationInput {
    private String projectName;
    private Date projectDate;
    private String country;
    private String city;
    private String zone;
    private String sector;
    private int width;                 // m
    private int length;                // m
    private int height;                // m
    private int airChangesPerHour;     // 1/h
    private int peopleCount;
    private Integer manualFreshAirRatio; // % optional
    private int heatRecoveryCount;
    private int waterHeaterCount;
    private int waterCoolerCount;
    private int summerDesignTemp;      // °C
    private int winterDesignTemp;      // °C
    private boolean automation;
    private DeviceModel deviceModel;

    private int filter2Count;
    private int silencerCount;
    private int filter3Count;
    private int steamHumidifierCount;
    private int electricHeaterCount;

    private int aspiratorCount;
    private int ventilatorCount;

    // getters & setters
    public int getFilter2Count() { return filter2Count; }
    public void setFilter2Count(int filter2Count) { this.filter2Count = filter2Count; }

    // Getters and setters
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public Date getProjectDate() { return projectDate; }
    public void setProjectDate(Date projectDate) { this.projectDate = projectDate; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getAirChangesPerHour() { return airChangesPerHour; }
    public void setAirChangesPerHour(int airChangesPerHour) { this.airChangesPerHour = airChangesPerHour; }
    public int getPeopleCount() { return peopleCount; }
    public void setPeopleCount(int peopleCount) { this.peopleCount = peopleCount; }

  //  public int getManualFreshAirRatio() { return manualFreshAirRatio; }
  //  public void setManualFreshAirRatio(Integer manualFreshAirRatio) { this.manualFreshAirRatio = manualFreshAirRatio; }

    public int getHeatRecoveryCount() { return heatRecoveryCount; }
    public void setHeatRecoveryCount(int heatRecoveryCount) { this.heatRecoveryCount = heatRecoveryCount; }
    public int getWaterHeaterCount() { return waterHeaterCount; }
    public void setWaterHeaterCount(int waterHeaterCount) { this.waterHeaterCount = waterHeaterCount; }
    public int getWaterCoolerCount() { return waterCoolerCount; }
    public void setWaterCoolerCount(int waterCoolerCount) { this.waterCoolerCount = waterCoolerCount; }

    public int getSummerDesignTemp() { return summerDesignTemp; }
    public void setSummerDesignTemp(int summerDesignTemp) { this.summerDesignTemp = summerDesignTemp; }
    public int getWinterDesignTemp() { return winterDesignTemp; }
    public void setWinterDesignTemp(int winterDesignTemp) { this.winterDesignTemp = winterDesignTemp; }

    public boolean isAutomation() { return automation; }
    public void setAutomation(boolean automation) { this.automation = automation; }

    public int getSilencerCount() {
        return silencerCount;
    }

    public void setSilencerCount(int silencerCount) {
        this.silencerCount = silencerCount;
    }

    public int getFilter3Count() {
        return filter3Count;
    }

    public void setFilter3Count(int filter3Count) {
        this.filter3Count = filter3Count;
    }

    public int getSteamHumidifierCount() {
        return steamHumidifierCount;
    }

    public void setSteamHumidifierCount(int steamHumidifierCount) {
        this.steamHumidifierCount = steamHumidifierCount;
    }

    public int getElectricHeaterCount() {
        return electricHeaterCount;
    }

    public void setElectricHeaterCount(int electricHeaterCount) {
        this.electricHeaterCount = electricHeaterCount;
    }

    public int getAspiratorCount() {
        return aspiratorCount;
    }

    public void setAspiratorCount(int aspiratorCount) {
        this.aspiratorCount = aspiratorCount;
    }

    public int getVentilatorCount() {
        return ventilatorCount;
    }

    public void setVentilatorCount(int ventilatorCount) {
        this.ventilatorCount = ventilatorCount;
    }

    public DeviceModel getDeviceModel() { return deviceModel; }
    public void setDeviceModel(DeviceModel deviceModel) { this.deviceModel = deviceModel; }
}