package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Enthält die Einstellungen der Libary:
 *      -gewünschte Präzension bei Iterationen
 *      -Implementierung des @{@link EKKostVerschCalculator}-Inteface
 */
public final class CFConfig {

    private static double precision = 0.001;

    private static EKKostVerschCalculator ekKostVerschCalculator = new TaxShieldEKKostVerschCalculator();

    /**
     * Konstruktor für CFConfig, der das Initieren verhindern, da alle Methoden statisch sind.
     */
    private CFConfig() {
    }

    /**
     * Gibt ein Objekt zurück, mit dem die EK-Kosten berechnet werden können.
     * 
     * @return Gibt eine Implementierung des @{@link EKKostVerschCalculator} zurück.
     */
    public static EKKostVerschCalculator getEkKostVerschCalculator() {
        return ekKostVerschCalculator;
    }

    public static void setEkKostVerschCalculator(final EKKostVerschCalculator ekKostVerschCalculator) {
        CFConfig.ekKostVerschCalculator = ekKostVerschCalculator;
    }

    /**
     * Getter Methode für die gewünschte Präzision.
     * 
     * @return Gibt die Präzision als double Wert zurück.
     */
    public static double getPrecision() {
        return precision;
    }

    /**
     * Setter Methode um die Präzision der Unternehmenswertberechnung über alle Verfahren hinweg gleich zu setzen.
     * @param precision double Wert für die Präzision; empfohlen: 0.001
     */
    public static void setPrecision(final double precision) {
        CFConfig.precision = precision;
    }
}
