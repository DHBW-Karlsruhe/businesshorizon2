package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.CFResult;
/**
 * Das Ergebnis einer CF Ermittlung nach dem Equity Verfahren
 *
 */
public interface EquityResult extends CFResult {

	/**
	 * @param periode Die Periode
	 * @return Die verschuldeten Eigenkapitalkosten
	 */
    double getEKKostVersch(final int periode);
}
