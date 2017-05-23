package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Stellt das Berechnungsergebnis eines CF Algorithmuses dar
 *
 */
public interface CFResult {

	/**
	 * @param periode Die Periode
	 * @return Den Unternehmenswert für diese Methode
	 */
	double getUnternehmenswert(int periode);

	/**
	 * @return Den Unternehmenswert für Periode 0
	 */
	default double getUnternehmenswertNow() {
		return getUnternehmenswert(0);
	}
}
