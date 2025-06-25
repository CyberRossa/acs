package org.example.calculation;

import java.util.Objects;

/**
 * Encapsulates the results of HVAC calculations.
 */
public class CalculationResult {
    private int winterBlowTemp;
    private int summerBlowTemp;
    private double freshAirAmount;
    private int freshAirRatio;
    private double aspiratorFlow;
    private double exhaustFlow;
    private double ventilatorFlow;
    private double heatingCapacity;
    private double coolingCapacity;
    private int outsideWinter;
    private int outsideSummerDry;
    private int outsideSummerWet;


    public CalculationResult() {}

    // Getters and Setters
    public double getFreshAirAmount() { return freshAirAmount; }
    public void setFreshAirAmount(double freshAirAmount) { this.freshAirAmount = freshAirAmount; }
    public int getFreshAirRatio() { return freshAirRatio; }
    public void setFreshAirRatio(int freshAirRatio) { this.freshAirRatio = freshAirRatio; }
    public double getAspiratorFlow() { return aspiratorFlow; }
    public void setAspiratorFlow(double aspiratorFlow) { this.aspiratorFlow = aspiratorFlow; }
    public double getExhaustFlow() { return exhaustFlow; }
    public void setExhaustFlow(double exhaustFlow) { this.exhaustFlow = exhaustFlow; }
    public double getVentilatorFlow() { return ventilatorFlow; }
    public void setVentilatorFlow(double ventilatorFlow) { this.ventilatorFlow = ventilatorFlow; }
    public double getHeatingCapacity() { return heatingCapacity; }
    public void setHeatingCapacity(double heatingCapacity) { this.heatingCapacity = heatingCapacity; }
    public double getCoolingCapacity() { return coolingCapacity; }
    public void setCoolingCapacity(double coolingCapacity) { this.coolingCapacity = coolingCapacity; }
    public int getOutsideWinter() { return outsideWinter; }
    public void setOutsideWinter(int outsideWinter) { this.outsideWinter = outsideWinter; }

    public int getOutsideSummerDry() { return outsideSummerDry; }
    public void setOutsideSummerDry(int outsideSummerDry) { this.outsideSummerDry = outsideSummerDry; }

    public int getOutsideSummerWet() { return outsideSummerWet; }
    public void setOutsideSummerWet(int outsideSummerWet) { this.outsideSummerWet = outsideSummerWet; }

    public int getWinterBlowTemp() { return winterBlowTemp; }
    public void setWinterBlowTemp(int winterBlowTemp) { this.winterBlowTemp = winterBlowTemp; }

    public int getSummerBlowTemp() { return summerBlowTemp; }
    public void setSummerBlowTemp(int summerBlowTemp) { this.summerBlowTemp = summerBlowTemp; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalculationResult)) return false;
        CalculationResult that = (CalculationResult) o;
        return Double.compare(that.winterBlowTemp, winterBlowTemp) == 0 &&
                Double.compare(that.summerBlowTemp, summerBlowTemp) == 0 &&
                Double.compare(that.freshAirAmount, freshAirAmount) == 0 &&
                freshAirRatio == that.freshAirRatio &&
                outsideWinter == that.outsideWinter &&
                outsideSummerDry == that.outsideSummerDry &&
                outsideSummerWet == that.outsideSummerWet &&
                Double.compare(that.aspiratorFlow, aspiratorFlow) == 0 &&
                Double.compare(that.exhaustFlow, exhaustFlow) == 0 &&
                Double.compare(that.ventilatorFlow, ventilatorFlow) == 0 &&
                Double.compare(that.heatingCapacity, heatingCapacity) == 0 &&
                Double.compare(that.coolingCapacity, coolingCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(winterBlowTemp, summerBlowTemp, outsideWinter, outsideSummerDry, outsideSummerWet,
                freshAirAmount, freshAirRatio, aspiratorFlow,
                exhaustFlow,ventilatorFlow,
                heatingCapacity, coolingCapacity);
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "winterBlowTemp=" + winterBlowTemp +
                ", summerBlowTemp=" + summerBlowTemp +
                ", outsideWinter=" + outsideWinter +
                ", outsideSummerDry=" + outsideSummerDry +
                ", outsideSummerWet=" + outsideSummerWet +
                ", freshAirAmount=" + freshAirAmount +
                ", freshAirRatio=" + freshAirRatio +
                ", aspiratorFlow=" + aspiratorFlow +
                ", exhaustFlow=" + exhaustFlow +
                ", ventilatorFlow=" + ventilatorFlow +
                ", heatingCapacity=" + heatingCapacity +
                ", coolingCapacity=" + coolingCapacity +
                '}';
    }
}