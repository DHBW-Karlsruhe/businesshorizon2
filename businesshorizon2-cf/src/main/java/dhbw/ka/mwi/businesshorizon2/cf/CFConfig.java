package dhbw.ka.mwi.businesshorizon2.cf;

public final class CFConfig {

    private static double precision = 0.001;

    private static EKKostVerschCalculator ekKostVerschCalculator = new TaxShieldEKKostVerschCalculator();

    /**
     * Konstruktor für CFConfig, der das Initieren verhindern, da alle Methoden statisch sind
     */
    private CFConfig() {
    }

    /**
     * getEKKostVerschCalculator gibt ein Objekt zurück, mit dem die EK Kosten berechnet werden können
     * 
     * @return gibt eine Implementierung des @{@link EKKostVerschCalculator} zurück
     */
    public static EKKostVerschCalculator getEkKostVerschCalculator() {
        return ekKostVerschCalculator;
    }

    public static void setEkKostVerschCalculator(final EKKostVerschCalculator ekKostVerschCalculator) {
        CFConfig.ekKostVerschCalculator = ekKostVerschCalculator;
    }

    /**
     * Getter Methode für die Präzision des Modells
     * 
     * @return gibt die Präzision als double Wert zurück
     */
    public static double getPrecision() {
        return precision;
    }

    /**
     * Setter Methode um die Präzision der Unternehmenswertberechnung über alle Verfahren hinweg gleich zu setzen
     * @param precision double Wert für die Präzision; empfohlen: 0.001
     */
    public static void setPrecision(final double precision) {
        CFConfig.precision = precision;
    }
}
