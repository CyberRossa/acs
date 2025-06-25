package org.example.model;

public class DeviceSelection {
    private String deviceGroup;
    private double airFlow;
    private double enthalpyWet;
    private double enthalpyDry;
    private String code;
    private String modelCode;
    private double price;
    private double motorPower;
    private String driveType;

    public String getDeviceGroup() { return deviceGroup; }
    public void setDeviceGroup(String deviceGroup) { this.deviceGroup = deviceGroup; }

    public double getAirFlow() { return airFlow; }
    public void setAirFlow(double airFlow) { this.airFlow = airFlow; }

    public double getEnthalpyWet() { return enthalpyWet; }
    public void setEnthalpyWet(double enthalpyWet) { this.enthalpyWet = enthalpyWet; }

    public double getEnthalpyDry() { return enthalpyDry; }
    public void setEnthalpyDry(double enthalpyDry) { this.enthalpyDry = enthalpyDry; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getMotorPower() { return motorPower; }
    public void setMotorPower(double motorPower) { this.motorPower = motorPower; }

    public String getDriveType() { return driveType; }
    public void setDriveType(String driveType) { this.driveType = driveType; }
}
