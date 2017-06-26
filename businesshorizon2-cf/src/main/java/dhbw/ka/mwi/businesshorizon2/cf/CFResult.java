package dhbw.ka.mwi.businesshorizon2.cf;

public class CFResult {
	
    private final double uWert;

    /**
     * Konstruktor für den aktuellen Unternehmenswert in Form eines double Werts
     * @param uWert der finale Unternehmenswert
     */
    CFResult(final double uWert) {
        this.uWert = uWert;
    }

    /**
     * Getter Methode um den Unternehmenswert zu erhalten
     * @return gibt den Unternehmenswert zurück
     */
    public double getuWert() {
        return uWert;
    }
}
