package org.example.model;

public class MahalParameters {
    public final double cycleFactor;
    // Konfor
    public final double comfortFreshPerPerson, comfortPressure, comfortMultiplier;
    // Hijyen
    public final double hygieneMinOutsidePct, hygieneMinFreshPct,
            hygienePressure, hygieneMultiplier;

    public MahalParameters(double cycleFactor,
                           double cFreshPerPerson, double cPressure, double cMultiplier,
                           double hMinOutside, double hMinFresh, double hPressure, double hMultiplier) {
        this.cycleFactor           = cycleFactor;
        this.comfortFreshPerPerson = cFreshPerPerson;
        this.comfortPressure       = cPressure;
        this.comfortMultiplier     = cMultiplier;
        this.hygieneMinOutsidePct  = hMinOutside;
        this.hygieneMinFreshPct    = hMinFresh;
        this.hygienePressure       = hPressure;
        this.hygieneMultiplier     = hMultiplier;
    }

}
