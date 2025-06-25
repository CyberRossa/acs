package org.example.calculation;

import org.example.services.DatabaseService;
import org.example.dao.EnthalpyCodeDAO;
import org.example.dao.FreshAirRateDAO;
import org.example.dao.MahallCevrimDAO;
import org.example.dao.SummerDesignConditionsDAO;
import org.example.dao.WinterDesignConditionsDAO;
import org.example.model.CalculationInput;
import org.example.calculation.CalculationResult;
import org.example.model.MahallCevrimEntry;

import java.sql.SQLException;

/**
 * JDBC-backed implementation of CalculationEngine:
 * - integer inputs
 * - uses double internally for precision
 * - rounds results to integer for output
 */
public class CalculationEngineImpl implements CalculationEngine {
    private final WinterDesignConditionsDAO winterDao;
    private final SummerDesignConditionsDAO summerDao;
    private final FreshAirRateDAO freshAirDao;
    private final MahallCevrimDAO mahalCevrimDao;
    private final EnthalpyCodeDAO enthalpyCodeDao;

    public CalculationEngineImpl(DatabaseService db) throws SQLException {
        this.winterDao       = new WinterDesignConditionsDAO(db);
        this.summerDao       = new SummerDesignConditionsDAO(db);
        this.freshAirDao     = new FreshAirRateDAO(db);
        this.mahalCevrimDao  = new MahallCevrimDAO(db);
        this.enthalpyCodeDao = new EnthalpyCodeDAO(db);
    }

    @Override
    public CalculationResult calculate(CalculationInput in) {
        CalculationResult result = new CalculationResult();

        // 1) Outside temperatures
        double outsideWinterD = winterDao.findKt(in.getCity())
                .orElseThrow(() -> new IllegalArgumentException("Winter outside temp not found"));
        double outsideSummerDryD = summerDao.findKt(in.getCity())
                .orElseThrow(() -> new IllegalArgumentException("Summer dry-bulb not found"));
        double outsideSummerWetD = summerDao.findYt(in.getCity())
                .orElseThrow(() -> new IllegalArgumentException("Summer wet-bulb not found"));
        result.setOutsideWinter((int)Math.round(outsideWinterD));
        result.setOutsideSummerDry((int)Math.round(outsideSummerDryD));
        result.setOutsideSummerWet((int)Math.round(outsideSummerWetD));

        // 2) Blow temperatures
        double TwD = in.getWinterDesignTemp() + 6;
        double TsD = in.getSummerDesignTemp() - 7;
        result.setWinterBlowTemp((int)Math.round(TwD));
        result.setSummerBlowTemp((int)Math.round(TsD));

        // 3) Mahall çevrim entry
        MahallCevrimEntry entry = mahalCevrimDao
                .findByZone(in.getZone())
                .orElseGet(() -> {
                    System.err.printf("Uyarı: zone '%s' bulunamadı, default kullanılıyor.%n", in.getZone());
                    return new MahallCevrimEntry(in.getZone(), 0.0, in.getAirChangesPerHour(), 101325, 1.0);
                });
        // 4) Total air flow (m³/h)
        double area      = in.getWidth() * in.getLength();
        double volume    = area * in.getHeight();
        double totalD    = volume * entry.getAirChanges();
        int    totalAir  = (int)Math.round(totalD);
        result.setVentilatorFlow(totalAir);

        // 5) Fresh air amount via MAHAL_CEVRIM_T_HAVA
        double freshD = calcFreshAirAmount(in, totalD, entry);
        int    fresh  = (int)Math.round(freshD);
        result.setFreshAirAmount(fresh);
        result.setFreshAirRatio((int)Math.ceil((freshD/totalD)*100));

        // 6) Aspirator flow (m³/h)
        double aspiratorD    = in.getAspiratorCount() * entry.getMultiplier() * totalD;
        int    aspiratorFlow = (int)Math.round(aspiratorD);
        result.setAspiratorFlow(aspiratorFlow);

        // 7) Exhaust flow (m³/h)
        int exhaust;
        if (aspiratorFlow == 0) exhaust = 0;
        else if (in.getHeatRecoveryCount() == 0) exhaust = aspiratorFlow - (totalAir - fresh);
        else exhaust = aspiratorFlow;
        result.setExhaustFlow(Math.max(exhaust, 0));

        // 8) Heating capacity (kW)
        double Tret    = in.getWinterDesignTemp() - 4;
        double Tmix    = (outsideWinterD*freshD + Tret*(totalD-freshD))/totalD;
        double heatD   = in.getWaterHeaterCount()>0 ? totalD*0.29*(TwD - Tmix) : 0;
        result.setHeatingCapacity((int)Math.round(heatD));

        // 9) Cooling capacity (kW)
        double coolD = 0;
        if (in.getWaterCoolerCount()>0) {
            int tag = (int)Math.round(TsD) + 3;
            String code = String.format("%d%d%d%d",
                    (int)Math.round(outsideSummerDryD),
                    (int)Math.round(outsideSummerWetD),
                    result.getFreshAirRatio(),
                    tag
            );
            double deltaH = enthalpyCodeDao
                    .findEnthalpyDiff(code)
                    .orElseThrow(() -> new IllegalArgumentException("Enthalpy diff not found for code " + code));
            coolD = totalD * 1.25 * deltaH;
        }
        result.setCoolingCapacity((int)Math.round(coolD));

        return result;
    }

    /**
     * Calculates fresh air (m³/h) from mahal_cevrim_t_hava values.
     */
    private double calcFreshAirAmount(
            CalculationInput in,
            double totalAirFlow,
            MahallCevrimEntry entry
    ) {
        // 1) air_changes yüzdesiyle hesapla
        double changePct = entry.getAirChanges();   // örn. %20 => 20.0
        if (changePct > 0) {
            return totalAirFlow * (changePct / 100.0);
        }

        // 2) kişi başı taze hava oranı
        double perPerson = entry.getPerPersonRate();
        if (perPerson > 0) {
            return in.getPeopleCount() * perPerson;
        }

        // 3) DB’den son çare oran çek ve uygula
        double fallback = freshAirDao
                .findRatePerPerson(in.getCountry(), in.getCity(), in.getSector())
                .orElse(0.0);
        return in.getPeopleCount() * fallback;
    }
}


/*package org.example.calculation;

import org.example.dao.*;
import org.example.model.SummerDesignCondition;
import org.example.services.DatabaseService;
import org.example.model.CalculationInput;
import org.example.model.MahallCevrimEntry;

public class CalculationEngineImpl implements CalculationEngine {
    private final OutsideConditionDAO outsideDao;
    private final FreshAirRateDAO freshAirDao;
    private final MahallCevrimDAO mahalCevrimDao;
    private final EnthalpyCodeDAO enthalpyCodeDao;

    public CalculationEngineImpl(DatabaseService db) {
        this.outsideDao      = new OutsideConditionDAO(db);
        this.freshAirDao     = new FreshAirRateDAO(db);
        this.mahalCevrimDao  = new MahallCevrimDAO(db);
        this.enthalpyCodeDao = new EnthalpyCodeDAO(db);
    }

    @Override
    public CalculationResult calculate(CalculationInput in) {
        CalculationResult result = new CalculationResult();

        // 1) Outside temperature (int)
        int outsideTemp = (int) Math.round(
                outsideDao.findTemperature(in.getCountry(), in.getCity())
                        .orElseThrow(() -> new IllegalArgumentException("Outside temperature not found"))
        );
        result.setOutsideTemp(outsideTemp);

        // 2) Blow temperatures
        int Tw = in.getWinterDesignTemp() + 6;
        int Ts = in.getSummerDesignTemp() - 7;
        result.setWinterBlowTemp(Tw);
        result.setSummerBlowTemp(Ts);

        // 3) Mahall çevrim
        MahallCevrimEntry entry = mahalCevrimDao
                .findBySectorAndZone(in.getSector(), in.getZone())
                .orElseThrow(() -> new IllegalArgumentException("Mahal çevrim not found"));

        // 4) Total air flow = area*height*changes
        int area = in.getWidth() * in.getLength();
        int volume = area * in.getHeight();
        int Qt = (int) (volume * entry.getAirChanges());
        result.setVentilatorFlow(Qt);

        // 5) Fresh air amount
        int Qf = calculateFreshAirAmount(in, Qt, entry);
        result.setFreshAirAmount(Qf);
        // Fresh air ratio (%)
        int R = (int) Math.ceil((Qf * 100.0) / Qt);
        result.setFreshAirRatio(R);

        // 6) Aspirator flow
        int Qa = calculateAspiratorFlow(in, Qt, entry);
        result.setAspiratorFlow(Qa);

        // 7) Exhaust flow
        int Qe = calculateExhaustFlow(in.getHeatRecoveryCount(), Qt, Qf, Qa);
        result.setExhaustFlow(Qe);

        // 8) Heating capacity (kW)
        int Ph = calculateHeatingCapacity(in, Qt, Tw, Qf);
        result.setHeatingCapacity(Ph);

        // 9) Cooling capacity (kW)
        int Pc = calculateCoolingCapacity(in, Qt, in.getSummerDesignTemp(), R, in);
        result.setCoolingCapacity(Pc);

        return result;
    }

    private int calculateFreshAirAmount(CalculationInput in, int Qt, MahallCevrimEntry entry) {
        if (in.getManualFreshAirRatio() != null) {
            return (int) Math.ceil((Qt * in.getManualFreshAirRatio()) / 100.0);
        }
        if (entry.getPerPersonRate() > 0) {
            return in.getPeopleCount() * (int) entry.getPerPersonRate();
        }
        double rate = freshAirDao
                .findRatePerPerson(in.getCountry(), in.getCity(), in.getSector())
                .orElse(0.0);
        return in.getPeopleCount() * (int) Math.round(rate);
    }

    private int calculateAspiratorFlow(CalculationInput in, int Qt, MahallCevrimEntry entry) {
        boolean hasAsp = in.getAirStreamConfigs().stream()
                .anyMatch(c -> "ASPIRATOR".equalsIgnoreCase(c.getStreamType()) && c.getCount() > 0);
        return hasAsp ? (int) Math.round(entry.getMultiplier() * Qt) : 0;
    }

    private int calculateExhaustFlow(int heatRecoveryCount, int Qt, int Qf, int Qa) {
        if (Qa == 0) {
            return 0;
        }
        if (heatRecoveryCount == 0) {
            return Qa - (Qt - Qf);
        }
        return Qa;
    }

    private int calculateHeatingCapacity(CalculationInput in, int Qt, int Tw, int Qf) {
        if (in.getWaterHeaterCount() == 0) return 0;
        int Tret = in.getWinterDesignTemp() - 4;
        int Tcity = (int) Math.round(
                outsideDao.findTemperature(in.getCountry(), in.getCity())
                        .orElseThrow(() -> new IllegalArgumentException("Outside temp not found"))
        );
        int Tmix = (Tcity * Qf + Tret * (Qt - Qf)) / Qt;
        return (int) Math.round(Qt * 0.29 * (Tw - Tmix));
    }

    private int calculateCoolingCapacity(CalculationInput in, int Qt, int Ts, int R) {
        if (in.getWaterCoolerCount() == 0) return 0;
        // Compose code without humidity: kt=Ts, ratio=R, blowOffset=Ts+3
        int tag = Ts + 3;
        String code = String.format("%d%d%d", Ts, R, tag);
        double deltaHD = enthalpyCodeDao.findEnthalpyDiff(code)
                .orElseThrow(() -> new IllegalArgumentException("Enthalpy diff not found for code " + code));
        double PcD = Qt * 1.25 * deltaHD;
        return (int) Math.round(PcD);
    }
} */