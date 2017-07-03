package dhbw.ka.mwi.businesshorizon2.cf;


class CFIntermediateResult {

    private final double[] uWert;
    private final double[] ekKost;

    /**
     * CFIntermediateResult speichert die übergebenen Unternehmenswerte und Eigenkapitalkosten
     * CFIntermediateResult wird verwendet, um Zwischenergebnisse aller Perioden zu speichern
     * 
     * @param uWert definiert die Unternehmenswerte als double Wert in einem Array
     * @param ekKost definiert die Eigenkapitalkosten als double Wert in einem Array
     */
    CFIntermediateResult(final double[] uWert, final double[] ekKost) {
        this.uWert = uWert;
        this.ekKost = ekKost;
    }

    /**
     * Getter Methode für den Unternehmenswert 
     * @return gibt die Unternehmenswerte als Array zurück
     */
    double[] getuWert() {
        return uWert;
    }

    /**
     * Getter Methode für die Eigenkapitalkosten
     * @return gibt die Eigenkapitalkosten als Array zurück
     */
    double[] getEkKost() {
        return ekKost;
    }
}
