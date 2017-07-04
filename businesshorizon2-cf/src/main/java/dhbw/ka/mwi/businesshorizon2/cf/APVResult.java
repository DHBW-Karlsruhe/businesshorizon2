package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Die Klasse erbt von CFResult und speichert alle Daten, die in der Berechnung des APV-Verfahrens vorkommen.
 * Enthält nur die Daten der spezifischen Periode für die der Unternehmenswert berechnet wurde.
 */
public class APVResult extends CFResult{

    private final double uwFiktiv;
    private final double taxShield;
    private final double gk;

    /**
     * All-args Konstruktor.
     * Über den super-Konstruktor wird das Ergebnis der Unternehmenswertberechnung mit übergeben.
     * 
     * @param uWert entspricht dem berechneten Unternehmenswert.
     * @param uwFiktiv entspricht dem fiktiven Unternehmenswert.
     * @param taxShield entspricht dem TaxShield.
     * @param gk entspricht dem Gesamtkapital.
     */
    APVResult(final double uWert, final double uwFiktiv, final double taxShield, final double gk) {
        super(uWert);
        this.uwFiktiv = uwFiktiv;
        this.taxShield = taxShield;
        this.gk = gk;
    }

    /**
     * Getter Methode für den fiktiven Unternehmenswert.
     * @return Gibt den fiktiven Unternehmenswert als double zurück.
     */
    public double getUwFiktiv() {
        return uwFiktiv;
    }

    /**
     * Getter Methode für das Tax Shield.
     * 
     * @return Gibt das Tax Shield als double zurück.
     */
    public double getTaxShield() {
        return taxShield;
    }

    /**
     * Getter Methode für das Gesamtkapital.
     * @return Gibt das Gesamtkapital als double zurück.
     */
    public double getGk() {
        return gk;
    }
}
