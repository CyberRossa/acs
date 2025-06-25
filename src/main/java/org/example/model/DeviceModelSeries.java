package org.example.model;



public enum DeviceModelSeries {
    MODEL_A("ACS"),
    MODEL_B("AHA"),
    MODEL_C("AHRV"),
    MODEL_D("AHRVT"),
    MODEL_E("ACSH"),
    MODEL_F("AHRVH"),
    MODEL_G("ACSP");

    private final String code;

    DeviceModelSeries(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    @Override
    public String toString() {
        return code;
    }
}
