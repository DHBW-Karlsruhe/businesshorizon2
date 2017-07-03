package dhbw.ka.mwi.businesshorizon2.cf;

final class TaxShieldCalculator {

	/**
	 * Konstruktor für den TaxShieldCalculator
	 */
    private TaxShieldCalculator() {
    }

    /**
     * calculateTaxShield berechnet das TaxShield
     * Basiert auf Formel 58a vom Ballwieser
     *
     * @param parameter enthält alle Parameter zur Berechnung des Unternehmenswerts
     * @param periode entspricht der Periode für die der Unternehmenswert berechnet werden soll
     * @return gibt das TaxShield der spezifischen Periode zurück
     */
    static double calculateTaxShield(final CFParameter parameter, final int periode) {
        double summe = 0;
        for (int i = periode + 1; i < parameter.numPerioden() - 1; i++) {
            summe += parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[i - 1] / Math.pow(parameter.getFKKosten() + 1, i - periode);
        }
        return summe + parameter.getuSteusatz() * parameter.getFK()[parameter.numPerioden() - 2] / Math.pow(parameter.getFKKosten() + 1, parameter.numPerioden() - 2 - Math.min(periode,parameter.numPerioden() - 2));
    }
}
