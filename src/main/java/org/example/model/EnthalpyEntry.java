package org.example.model;

public class EnthalpyEntry {
    private final double kt;
    private final double yt;
    private final double tazeHavaOrani;
    private final String concate;
    private final double tazeHava;
    private final double donusHavasi;
    private final double donusHavasiEnthalpy;
    private final double kt2;
    private final double yt2;
    private final double tmixEnthalpy;
    private final double islemeEnthalpy15c;
    private final double enthalpyDiff;

    public EnthalpyEntry(double kt, double yt, double tazeHavaOrani, String concate,
                         double tazeHava, double donusHavasi, double donusHavasiEnthalpy,
                         double kt2, double yt2, double tmixEnthalpy,
                         double islemeEnthalpy15c, double enthalpyDiff) {
        this.kt = kt;
        this.yt = yt;
        this.tazeHavaOrani = tazeHavaOrani;
        this.concate = concate;
        this.tazeHava = tazeHava;
        this.donusHavasi = donusHavasi;
        this.donusHavasiEnthalpy = donusHavasiEnthalpy;
        this.kt2 = kt2;
        this.yt2 = yt2;
        this.tmixEnthalpy = tmixEnthalpy;
        this.islemeEnthalpy15c = islemeEnthalpy15c;
        this.enthalpyDiff = enthalpyDiff;
    }

    // getter’lar...
    public double getEnthalpyDiff() { return enthalpyDiff; }
    // diğer getter’lar da ihtiyaca göre eklenebilir
}
