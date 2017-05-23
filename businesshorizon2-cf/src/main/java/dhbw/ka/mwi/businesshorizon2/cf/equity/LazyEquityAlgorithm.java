package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
/**
 * Diese Equity Implementierung ermittelt das CF Ergebnis erst beim Aufruf der Methoden im Result-Object. Die Werte nach dem ersten Aufruf in einem Cache gehalten. 
 *
 */
public class LazyEquityAlgorithm implements CFAlgorithm<EquityResult> {

	@Override
	public EquityResult calculate(final CFParameter parameter) {
		return new LazyCachedEquityResult(parameter);
	}

}
