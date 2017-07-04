package dhbw.ka.mwi.businesshorizon2.cf;

final class TaxShieldCalculator {

	/**
	 * Verhinder von Objekten, da alle Methoden statisch sind.
	 */
    private TaxShieldCalculator() {
    }

    /**
     * Berechnet das TaxShield.
     * Basiert auf Formel 58a vom Ballwieser.
     *
     * @param parameter enthält alle Parameter zur Berechnung des Unternehmenswerts.
     * @param periode entspricht der Periode für die der Unternehmenswert berechnet werden soll.
     * @return Gibt das TaxShield der spezifischen Periode zurück.
     */
    static double calculateTaxShield(final CFParameter parameter, final int periode) {
        double summe = 0;
        for (int i = periode + 1; i < parameter.numPerioden() - 1; i++) {
            summe += parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[i - 1] / Math.pow(parameter.getFKKosten() + 1, i - periode);
        }
        return summe + parameter.getuSteusatz() * parameter.getFK()[parameter.numPerioden() - 2] / Math.pow(parameter.getFKKosten() + 1, parameter.numPerioden() - 2 - Math.min(periode,parameter.numPerioden() - 2));
    }
}
