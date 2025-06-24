package org.example.calculation;

public class CalculationResult {  private double winterBlowTemp;      // KIŞ ÜFLEME SICAKLIĞI
    private double summerBlowTemp;      // YAZ ÜFLEME SICAKLIĞI
    private double outsideTemp;         // Dış şart sıcaklığı
    private double freshAirAmount;      // Temiz hava miktarı (m³/s)
    private int freshAirRatio;          // Taze hava oranı (% - yukarı yuvarlanmış)
    private double aspiratorFlow;       // Aspiratör hava debisi (m³/s)
    private double exhaustFlow;         // Egzost hava debisi (m³/s)
    private double freshAirFlow;        // Taze hava debisi (m³/s)
    private double ventilatorFlow;      // Vantilatör hava debisi (m³/s)
    private double heatingCapacity;     // Isıtma kapasitesi (kW)
    private double coolingCapacity;     // Soğutma kapasitesi (kW)

    // Getters and setters
    public double getWinterBlowTemp() { return winterBlowTemp; }
    public void setWinterBlowTemp(double winterBlowTemp) { this.winterBlowTemp = winterBlowTemp; }

    public double getSummerBlowTemp() { return summerBlowTemp; }
    public void setSummerBlowTemp(double summerBlowTemp) { this.summerBlowTemp = summerBlowTemp; }

    public double getOutsideTemp() { return outsideTemp; }
    public void setOutsideTemp(double outsideTemp) { this.outsideTemp = outsideTemp; }

    public double getFreshAirAmount() { return freshAirAmount; }
    public void setFreshAirAmount(double freshAirAmount) { this.freshAirAmount = freshAirAmount; }

    public int getFreshAirRatio() { return freshAirRatio; }
    public void setFreshAirRatio(int freshAirRatio) { this.freshAirRatio = freshAirRatio; }

    public double getAspiratorFlow() { return aspiratorFlow; }
    public void setAspiratorFlow(double aspiratorFlow) { this.aspiratorFlow = aspiratorFlow; }

    public double getExhaustFlow() { return exhaustFlow; }
    public void setExhaustFlow(double exhaustFlow) { this.exhaustFlow = exhaustFlow; }

    public double getFreshAirFlow() { return freshAirFlow; }
    public void setFreshAirFlow(double freshAirFlow) { this.freshAirFlow = freshAirFlow; }

    public double getVentilatorFlow() { return ventilatorFlow; }
    public void setVentilatorFlow(double ventilatorFlow) { this.ventilatorFlow = ventilatorFlow; }

    public double getHeatingCapacity() { return heatingCapacity; }
    public void setHeatingCapacity(double heatingCapacity) { this.heatingCapacity = heatingCapacity; }

    public double getCoolingCapacity() { return coolingCapacity; }
    public void setCoolingCapacity(double coolingCapacity) { this.coolingCapacity = coolingCapacity; }
}
