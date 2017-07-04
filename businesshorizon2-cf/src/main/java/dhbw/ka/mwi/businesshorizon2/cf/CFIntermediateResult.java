package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Speichert die übergebenen Unternehmenswerte und Eigenkapitalkosten.
 * Wird verwendet, um Zwischenergebnisse aller Perioden zu speichern.
 */
class CFIntermediateResult {

    private final double[] uWert;
    private final double[] ekKost;

    /**
     *  All args Konstruktor
     * @param uWert definiert die Unternehmenswerte als double Wert in einem Array.
     * @param ekKost definiert die Eigenkapitalkosten als double Wert in einem Array.
     */
    CFIntermediateResult(final double[] uWert, final double[] ekKost) {
        this.uWert = uWert;
        this.ekKost = ekKost;
    }

    /**
     * Getter Methode für den Unternehmenswert.
     * @return Gibt die Unternehmenswerte als Array zurück.
     */
    double[] getuWert() {
        return uWert;
    }

    /**
     * Getter Methode für die Eigenkapitalkosten.
     * @return Gibt die Eigenkapitalkosten als Array zurück.
     */
    double[] getEkKost() {
        return ekKost;
    }
}
