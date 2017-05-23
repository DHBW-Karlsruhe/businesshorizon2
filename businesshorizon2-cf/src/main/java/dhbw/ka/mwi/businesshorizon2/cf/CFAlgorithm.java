package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Ein Cashflow Algorithmus
 *
 * @param <T> Der konkrete Rückgabetyp der Berechnung
 */
@FunctionalInterface
public interface CFAlgorithm<T extends CFResult> {
	/**
	 * Führt eine Cashflow Berechnung durch
	 * @param parameter Die Paramter
	 * @return Das Ergebniss der Cashflow Berechnung
	 */
    T calculate(CFParameter parameter);
}
