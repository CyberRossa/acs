package org.example.calculation;

import org.example.model.ACSPLUSInput;

public class CalculationEngineImpl implements CalculationEngine { @Override
public CalculationResult calculate(ACSPLUSInput input) {
    CalculationResult result = new CalculationResult();

    // Üfleme sıcaklıkları
    result.setWinterBlowTemp(calcWinterBlowTemp(input.getWinterDesignTemp()));
    result.setSummerBlowTemp(calcSummerBlowTemp(input.getSummerDesignTemp()));

    // Dış şart
    result.setOutsideTemp(fetchOutsideTemperature(input.getCountry(), input.getCity()));

    // Temiz hava miktarı ve oranı
    double freshAirAmount = calcFreshAirAmount(input);
    result.setFreshAirAmount(freshAirAmount);
    result.setFreshAirRatio(calcFreshAirRatio(freshAirAmount, input));

    // Hava debileri
    result.setAspiratorFlow(calcAspiratorFlow(input.getAspiratorCount(), input.getAirChangesPerHour()));
    result.setExhaustFlow(calcExhaustFlow(result.getAspiratorFlow(), freshAirAmount, input.getAirChangesPerHour()));
    result.setFreshAirFlow(calcFreshAirFlow(result.getAspiratorFlow(), input.getAirChangesPerHour()));
    result.setVentilatorFlow(calcVentilatorFlow(input.getVentilatorCount(), input.getAirChangesPerHour()));

    // Kapasiteler
    result.setHeatingCapacity(calcHeatingCapacity(
            input.getWaterCoolerCount(), input.getAirChangesPerHour(),
            result.getWinterBlowTemp(), result.getOutsideTemp(), input.getWinterDesignTemp()));
    result.setCoolingCapacity(calcCoolingCapacity(
            input.getWaterCoolerCount(), input.getAirChangesPerHour(),
            input.getSummerDesignTemp(), input.getSummerDesignHumidity()));

    return result;
}

    private double calcWinterBlowTemp(double winterDesignTemp) {
        // KULLANICININ GİRDİĞİ İSTENİLEN MAHAL SICAKLIĞI + 6
        return winterDesignTemp + 6;
    }

    private double calcSummerBlowTemp(double summerDesignTemp) {
        // KULLANICININ GİRDİĞİ İSTENİLEN MAHAL SICAKLIĞI - 7
        return summerDesignTemp - 7;
    }

    private double fetchOutsideTemperature(String country, String city) {
        // Dış hava sıcaklığı DB veya servis çağrısı
        // TODO: implement data fetch
        return 0;
    }

    private double calcFreshAirAmount(ACSPLUSInput input) {
        // TODO: implement temiz hava miktarı formülü
        return 0;
    }

    private int calcFreshAirRatio(double freshAirAmount, ACSPLUSInput input) {
        // TODO: implement taze hava oranı formülü (yukarı yuvarlama)
        return 0;
    }

    private double calcAspiratorFlow(int aspiratorCount, double airChangesPerHour) {
        // TODO: implement aspiratör hava debisi formülü
        return 0;
    }

    private double calcExhaustFlow(double aspiratorFlow, double freshAirAmount, double airChangesPerHour) {
        // TODO: implement egzost hava debisi formülü
        return 0;
    }

    private double calcFreshAirFlow(double aspiratorFlow, double airChangesPerHour) {
        // TODO: implement taze hava debisi formülü
        return 0;
    }

    private double calcVentilatorFlow(int ventilatorCount, double airChangesPerHour) {
        // TODO: implement vantilatör hava debisi formülü
        return ventilatorCount * airChangesPerHour;
    }

    private double calcHeatingCapacity(int waterCoolerCount, double airChangesPerHour,
                                       double winterBlowTemp, double outsideTemp, double winterDesignTemp) {
        // TODO: implement ısıtma kapasitesi formülü (sulu su soğutucu ve üfleme/kullanılan verilerle)
        return 0;
    }

    private double calcCoolingCapacity(int waterCoolerCount, double airChangesPerHour,
                                       double summerDesignTemp, double summerDesignHumidity) {
        // TODO: implement soğutma kapasitesi formülü (enthalpi tablosu verileri ile)
        return 0;
    }
}
