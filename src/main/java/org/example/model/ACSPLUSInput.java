package org.example.model;

import java.util.List;
import java.util.Objects;

public class ACSPLUSInput {

    // Konum & mahal
    private String country;                // Ülke
    private String city;                   // Şehir
    private String zone;                   // Mahal (örn. Salon, Ofis)
    private String sector;                 // Sektör (örn. Konfor)

    // Hacim & alan
    private double area;                   // Alan (m²)
    private double length;                 // Boy (m)
    private double width;                  // Genişlik (m)
    private double height;                 // Yükseklik (m)
    private double airChangesPerHour;      // Hava çevrim sayısı

    // İnsan & temiz hava
    private int peopleCount;               // Kişi sayısı
    private Double manualFreshAirRatio;    // Manuel taze hava oranı (%) — null ise DB’den çekilen değer kullanılacak

    // Filtre & susturucu (3. filtre) sayısal
    private int secondFilterCount;         // 2. filtre adedi
    private int silencerCount;             // Susturucu (3. filtre) adedi

    // Isı geri kazanım
    private int heatRecovery;              // IGK (ısı geri kazanımı) değeri sayısal

    // Isıtıcılar
    private int waterHeaterCount;          // Sulu ısıtıcı adedi
    private int electricHeaterCount;       // Elektrikli ısıtıcı adedi

    // Soğutucu / nemlendirici
    private int waterCoolerCount;          // Sulu soğutucu adedi
    private int steamHumidifierCount;      // Buharlı nemlendirici adedi

    // Aspiratör & vantilatör
    private int aspiratorCount;            // Aspiratör sayısı
    private int ventilatorCount;           // Vantilatör sayısı

    // Tasarım şartları
    private double summerDesignTemp;       // Yaz mahal şartı sıcaklık (°C)
    private double summerDesignHumidity;   // Yaz mahal şartı bağıl nem (%)
    private double winterDesignTemp;       // Kış mahal şartı sıcaklık (°C)
    private double winterDesignHumidity;   // Kış mahal şartı bağıl nem (%)

    // Kontrol & sistemler
    private boolean remoteControl;         // Kumanda kontrolü
    private boolean automation;            // Otomasyon
    private CoolingSystem coolingSystem;   // Soğutma sistemi tipi (enum)

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAirChangesPerHour() {
        return airChangesPerHour;
    }

    public void setAirChangesPerHour(double airChangesPerHour) {
        this.airChangesPerHour = airChangesPerHour;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Double getManualFreshAirRatio() {
        return manualFreshAirRatio;
    }

    public void setManualFreshAirRatio(Double manualFreshAirRatio) {
        this.manualFreshAirRatio = manualFreshAirRatio;
    }

    public int getSecondFilterCount() {
        return secondFilterCount;
    }

    public void setSecondFilterCount(int secondFilterCount) {
        this.secondFilterCount = secondFilterCount;
    }

    public int getSilencerCount() {
        return silencerCount;
    }

    public void setSilencerCount(int silencerCount) {
        this.silencerCount = silencerCount;
    }

    public int getHeatRecovery() {
        return heatRecovery;
    }

    public void setHeatRecovery(int heatRecovery) {
        this.heatRecovery = heatRecovery;
    }

    public int getWaterHeaterCount() {
        return waterHeaterCount;
    }

    public void setWaterHeaterCount(int waterHeaterCount) {
        this.waterHeaterCount = waterHeaterCount;
    }

    public int getElectricHeaterCount() {
        return electricHeaterCount;
    }

    public void setElectricHeaterCount(int electricHeaterCount) {
        this.electricHeaterCount = electricHeaterCount;
    }

    public int getWaterCoolerCount() {
        return waterCoolerCount;
    }

    public void setWaterCoolerCount(int waterCoolerCount) {
        this.waterCoolerCount = waterCoolerCount;
    }

    public int getSteamHumidifierCount() {
        return steamHumidifierCount;
    }

    public void setSteamHumidifierCount(int steamHumidifierCount) {
        this.steamHumidifierCount = steamHumidifierCount;
    }

    public int getAspiratorCount() {
        return aspiratorCount;
    }

    public void setAspiratorCount(int aspiratorCount) {
        this.aspiratorCount = aspiratorCount;
    }

    public int getVentilatorCount() {
        return ventilatorCount;
    }

    public void setVentilatorCount(int ventilatorCount) {
        this.ventilatorCount = ventilatorCount;
    }

    public double getSummerDesignTemp() {
        return summerDesignTemp;
    }

    public void setSummerDesignTemp(double summerDesignTemp) {
        this.summerDesignTemp = summerDesignTemp;
    }

    public double getSummerDesignHumidity() {
        return summerDesignHumidity;
    }

    public void setSummerDesignHumidity(double summerDesignHumidity) {
        this.summerDesignHumidity = summerDesignHumidity;
    }

    public double getWinterDesignTemp() {
        return winterDesignTemp;
    }

    public void setWinterDesignTemp(double winterDesignTemp) {
        this.winterDesignTemp = winterDesignTemp;
    }

    public double getWinterDesignHumidity() {
        return winterDesignHumidity;
    }

    public void setWinterDesignHumidity(double winterDesignHumidity) {
        this.winterDesignHumidity = winterDesignHumidity;
    }

    public boolean isRemoteControl() {
        return remoteControl;
    }

    public void setRemoteControl(boolean remoteControl) {
        this.remoteControl = remoteControl;
    }

    public boolean isAutomation() {
        return automation;
    }

    public void setAutomation(boolean automation) {
        this.automation = automation;
    }

    public CoolingSystem getCoolingSystem() {
        return coolingSystem;
    }

    public void setCoolingSystem(CoolingSystem coolingSystem) {
        this.coolingSystem = coolingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ACSPLUSInput that = (ACSPLUSInput) o;
        return Double.compare(area, that.area) == 0 && Double.compare(length, that.length) == 0 && Double.compare(width, that.width) == 0 && Double.compare(height, that.height) == 0 && Double.compare(airChangesPerHour, that.airChangesPerHour) == 0 && peopleCount == that.peopleCount && secondFilterCount == that.secondFilterCount && silencerCount == that.silencerCount && heatRecovery == that.heatRecovery && waterHeaterCount == that.waterHeaterCount && electricHeaterCount == that.electricHeaterCount && waterCoolerCount == that.waterCoolerCount && steamHumidifierCount == that.steamHumidifierCount && aspiratorCount == that.aspiratorCount && ventilatorCount == that.ventilatorCount && Double.compare(summerDesignTemp, that.summerDesignTemp) == 0 && Double.compare(summerDesignHumidity, that.summerDesignHumidity) == 0 && Double.compare(winterDesignTemp, that.winterDesignTemp) == 0 && Double.compare(winterDesignHumidity, that.winterDesignHumidity) == 0 && remoteControl == that.remoteControl && automation == that.automation && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(zone, that.zone) && Objects.equals(sector, that.sector) && Objects.equals(manualFreshAirRatio, that.manualFreshAirRatio) && coolingSystem == that.coolingSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, zone, sector, area, length, width, height, airChangesPerHour, peopleCount, manualFreshAirRatio, secondFilterCount, silencerCount, heatRecovery, waterHeaterCount, electricHeaterCount, waterCoolerCount, steamHumidifierCount, aspiratorCount, ventilatorCount, summerDesignTemp, summerDesignHumidity, winterDesignTemp, winterDesignHumidity, remoteControl, automation, coolingSystem);
    }

    @Override
    public String toString() {
        return "ACSPLUSInput{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zone='" + zone + '\'' +
                ", sector='" + sector + '\'' +
                ", area=" + area +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", airChangesPerHour=" + airChangesPerHour +
                ", peopleCount=" + peopleCount +
                ", manualFreshAirRatio=" + manualFreshAirRatio +
                ", secondFilterCount=" + secondFilterCount +
                ", silencerCount=" + silencerCount +
                ", heatRecovery=" + heatRecovery +
                ", waterHeaterCount=" + waterHeaterCount +
                ", electricHeaterCount=" + electricHeaterCount +
                ", waterCoolerCount=" + waterCoolerCount +
                ", steamHumidifierCount=" + steamHumidifierCount +
                ", aspiratorCount=" + aspiratorCount +
                ", ventilatorCount=" + ventilatorCount +
                ", summerDesignTemp=" + summerDesignTemp +
                ", summerDesignHumidity=" + summerDesignHumidity +
                ", winterDesignTemp=" + winterDesignTemp +
                ", winterDesignHumidity=" + winterDesignHumidity +
                ", remoteControl=" + remoteControl +
                ", automation=" + automation +
                ", coolingSystem=" + coolingSystem +
                '}';
    }
}



