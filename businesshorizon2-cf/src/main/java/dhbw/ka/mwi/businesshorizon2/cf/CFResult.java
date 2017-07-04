package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Speicherung des Unternehmenswertes als Ergebnis der DFC-Verfahren.
 */
public class CFResult {
	
    private final double uWert;

    /**
     * Konstruktor für den aktuellen Unternehmenswert in Form eines double Wertes.
     * @param uWert der Unternehmenswert.
     */
    CFResult(final double uWert) {
        this.uWert = uWert;
    }

    /**
     * Getter Methode um den Unternehmenswert zu erhalten.
     * @return Gibt den Unternehmenswert zurück.
     */
    public double getuWert() {
        return uWert;
    }
}
