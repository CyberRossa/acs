package org.example.model;

import java.util.Objects;

public class MahallCevrimEntry {

        private final String zoneName;
        private final double perPersonRate;
        private final double airChanges;
        private final double pressure;
        private final double multiplier;

        // Parametreli yapıcı
        public MahallCevrimEntry(String zoneName,
                                 double perPersonRate,
                                 double airChanges,
                                 double pressure,
                                 double multiplier) {
            this.zoneName      = zoneName;
            this.perPersonRate = perPersonRate;
            this.airChanges    = airChanges;
            this.pressure      = pressure;
            this.multiplier    = multiplier;
        }

        // Getter’lar
        public String getZoneName()       { return zoneName;      }
        public double getPerPersonRate()  { return perPersonRate; }
        public double getAirChanges()     { return airChanges;    }
        public double getPressure()       { return pressure;      }
        public double getMultiplier()     { return multiplier;    }

        // equals / hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MahallCevrimEntry)) return false;
            MahallCevrimEntry that = (MahallCevrimEntry) o;
            return Double.compare(that.perPersonRate, perPersonRate) == 0
                    && Double.compare(that.airChanges,    airChanges)    == 0
                    && Double.compare(that.pressure,      pressure)      == 0
                    && Double.compare(that.multiplier,    multiplier)    == 0
                    && Objects.equals(zoneName, that.zoneName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(zoneName, perPersonRate, airChanges, pressure, multiplier);
        }

        @Override
        public String toString() {
            return "MahallCevrimEntry{" +
                    "zoneName='"      + zoneName      + '\'' +
                    ", perPersonRate=" + perPersonRate +
                    ", airChanges="    + airChanges    +
                    ", pressure="      + pressure      +
                    ", multiplier="    + multiplier    +
                    '}';
        }
    }
