package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.CFResult;

/**
 * Das Ergebnis einer CF Ermittlung nach dem Entity Verfahren
 *
 */
public interface EntityResult extends CFResult {
	/**
	 * @param periode Die Periode
	 * @return Der WACC
	 */
    double getWACC(final int periode);
}
