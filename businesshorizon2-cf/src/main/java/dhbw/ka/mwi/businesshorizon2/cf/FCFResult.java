package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Die Klasse erbt von CFResult und speichert alle Daten, die in der Berechnung des FCF-Verfahrens vorkommen.
 * Enthält nur die Daten der spezifischen Periode für die der Unternehmenswert berechnet wurde.
 */
public class FCFResult extends CFResult {
    private final double gk;

    /**
     * Konstruktor für das FCF Result.
     * Speichert den Unternehmenswert und das Gesamtkapital.
     * 
     * @param uWert entspricht dem zu speichernden Unternehmenswert.
     * @param gk entspricht dem Gesamtkapital.
     */
    FCFResult(final double uWert, final double gk) {
        super(uWert);
        this.gk = gk;
    }

    /**
     * Getter Methode für das Gesamtkapital.
     * @return Gibt das Gesamtkapital als double Wert zurück.
     */
    public double getGk() {
        return gk;
    }
}
