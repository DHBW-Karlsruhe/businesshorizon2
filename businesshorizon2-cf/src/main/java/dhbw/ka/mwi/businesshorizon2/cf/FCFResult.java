package dhbw.ka.mwi.businesshorizon2.cf;

public class FCFResult extends CFResult {
    private final double gk;

    /**
     * Konstruktor für das FCF Result
     * Speichert den Unternehmenswert und das Gesamtkapital
     * 
     * @param uWert entspricht dem zu speichernden Unternehmenswert
     * @param gk entspricht dem Gesamtkapital
     */
    FCFResult(final double uWert, final double gk) {
        super(uWert);
        this.gk = gk;
    }

    /**
     * Getter Methode für das Gesamtkapital
     * @return gibt das Gesamtkapital als double Wert zurück
     */
    public double getGk() {
        return gk;
    }
}
