package org.example.model;

import java.util.List;

public class ACSPLUSOutput {

    public String mahalName;             // Mahal adı
    public double enthalpy;              // Entalpi
    public double winterSetpoint;        // Kış setpoint

    // Airflow outputs
    public double totalAirflow;
    public double freshAir;
    public double freshAirPercent;
    public double freshAirVolume;
    public double summerSupplyTemp;
    public double winterSupplyTemp;
    public double internalTemp;
    public double totalLoad;

    // Santral / mixing outputs
    public double c24;
    public double d24;
    public double m24;
    public double e24;

    // Condensing unit
    public boolean condensingEnabled;
    public double condensingPower;

    // Device selection
    public String filter1;
    public String filter2;
    public double deviceTotalPrice;
    public String extraDevice;

    // Automation items
    public List<org.example.model.AutomationItem> automationItems;

    // Pricing & panels
    public double ahuPrice;
    public double panoSizing;
    public double automationPower;

    // Final loads
    public double heatingLoad;
    public double coolingLoad;
}

