package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
/**
 * Diese Entity Implementierung ermittelt das CF Ergebnis erst beim Aufruf der Methoden im Result-Object. Die Werte nach dem ersten Aufruf in einem Cache gehalten. 
 *
 */
public class LazyEntityAlgorithm implements CFAlgorithm<EntityResult> {

	@Override
	public EntityResult calculate(final CFParameter parameter) {
		return new LazyCachedEntityResult(parameter);
	}

}
