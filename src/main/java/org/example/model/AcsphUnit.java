package org.example.model;

public class AcsphUnit {
    private String code;
    private String model;
    private String compressorModel;
    private int compressorQty;
    private double airFlow;
    private double heatingPower;
    private double current;
    private double outdoorDryTemp;
    private double evaporationCapacity;
    private double condensationCapacity;
    private double laborPrice;
    private double devicePrice;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getCompressorModel() { return compressorModel; }
    public void setCompressorModel(String compressorModel) { this.compressorModel = compressorModel; }

    public int getCompressorQty() { return compressorQty; }
    public void setCompressorQty(int compressorQty) { this.compressorQty = compressorQty; }

    public double getAirFlow() { return airFlow; }
    public void setAirFlow(double airFlow) { this.airFlow = airFlow; }

    public double getHeatingPower() { return heatingPower; }
    public void setHeatingPower(double heatingPower) { this.heatingPower = heatingPower; }

    public double getCurrent() { return current; }
    public void setCurrent(double current) { this.current = current; }

    public double getOutdoorDryTemp() { return outdoorDryTemp; }
    public void setOutdoorDryTemp(double outdoorDryTemp) { this.outdoorDryTemp = outdoorDryTemp; }

    public double getEvaporationCapacity() { return evaporationCapacity; }
    public void setEvaporationCapacity(double evaporationCapacity) { this.evaporationCapacity = evaporationCapacity; }

    public double getCondensationCapacity() { return condensationCapacity; }
    public void setCondensationCapacity(double condensationCapacity) { this.condensationCapacity = condensationCapacity; }

    public double getLaborPrice() { return laborPrice; }
    public void setLaborPrice(double laborPrice) { this.laborPrice = laborPrice; }

    public double getDevicePrice() { return devicePrice; }
    public void setDevicePrice(double devicePrice) { this.devicePrice = devicePrice; }
}