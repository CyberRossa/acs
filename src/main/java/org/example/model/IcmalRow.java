package org.example.model;

import javafx.beans.property.*;

public class IcmalRow {
    private final StringProperty device = new SimpleStringProperty();
    private final DoubleProperty capacity = new SimpleDoubleProperty();
    private final DoubleProperty airflow = new SimpleDoubleProperty();
    private final DoubleProperty power = new SimpleDoubleProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();

    // Getter/Setter/Property metotları

    public String getDevice() { return device.get(); }
    public void setDevice(String device) { this.device.set(device); }
    public StringProperty deviceProperty() { return device; }

    public double getCapacity() { return capacity.get(); }
    public void setCapacity(double capacity) { this.capacity.set(capacity); }
    public DoubleProperty capacityProperty() { return capacity; }

    public double getAirflow() { return airflow.get(); }
    public void setAirflow(double airflow) { this.airflow.set(airflow); }
    public DoubleProperty airflowProperty() { return airflow; }

    public double getPower() { return power.get(); }
    public void setPower(double power) { this.power.set(power); }
    public DoubleProperty powerProperty() { return power; }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }
    public DoubleProperty priceProperty() { return price; }

    public double getTotal() { return total.get(); }
    public void setTotal(double total) { this.total.set(total); }
    public DoubleProperty totalProperty() { return total; }
}
