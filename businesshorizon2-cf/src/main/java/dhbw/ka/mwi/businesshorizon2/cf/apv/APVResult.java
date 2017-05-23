package dhbw.ka.mwi.businesshorizon2.cf.apv;

import dhbw.ka.mwi.businesshorizon2.cf.CFResult;

/**
 * Das Ergebnis einer CF Ermittlung nach dem APV Verfahren
 *
 */
public interface APVResult extends CFResult {
	/**
	 * @param periode Die Periode
	 * @return Der TaxShield für die Periode
	 */
    double getTaxShield(final int periode);

	/**
	 * @param periode Die Periode
	 * @return Der Marktwert des Fremdkapitals für die Periode
	 */
    double getMarktwertFK(final int periode);
}
