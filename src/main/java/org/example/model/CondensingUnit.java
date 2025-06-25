package org.example.model;

public class CondensingUnit {
    private String compressorModel;
    private int compressorQty;
    private String deviceModel;
    private double coolingCapacity;
    private double heatingPower;
    private double current;
    private double unitPrice;
    private double totalPrice;
    private double laborCost;
    private double devicePrice;

    public String getCompressorModel() { return compressorModel; }
    public void setCompressorModel(String compressorModel) { this.compressorModel = compressorModel; }

    public int getCompressorQty() { return compressorQty; }
    public void setCompressorQty(int compressorQty) { this.compressorQty = compressorQty; }

    public String getDeviceModel() { return deviceModel; }
    public void setDeviceModel(String deviceModel) { this.deviceModel = deviceModel; }

    public double getCoolingCapacity() { return coolingCapacity; }
    public void setCoolingCapacity(double coolingCapacity) { this.coolingCapacity = coolingCapacity; }

    public double getHeatingPower() { return heatingPower; }
    public void setHeatingPower(double heatingPower) { this.heatingPower = heatingPower; }

    public double getCurrent() { return current; }
    public void setCurrent(double current) { this.current = current; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public double getLaborCost() { return laborCost; }
    public void setLaborCost(double laborCost) { this.laborCost = laborCost; }

    public double getDevicePrice() { return devicePrice; }
    public void setDevicePrice(double devicePrice) { this.devicePrice = devicePrice; }
}
