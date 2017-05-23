package dhbw.ka.mwi.businesshorizon2.cf.apv;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Diese APV Implementierung ermittelt das CF Ergebnis erst beim Aufruf der Methoden im Result-Object. 
 *
 */
public class LazyAPVAlgorithm implements CFAlgorithm<APVResult> {

	@Override
	public APVResult calculate(final CFParameter parameter) {
		return new LazyAPVResult(parameter);
	}

}
