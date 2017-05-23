package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.apv.APVResult;
import dhbw.ka.mwi.businesshorizon2.cf.entity.EntityResult;
import dhbw.ka.mwi.businesshorizon2.cf.equity.EquityResult;

/**
 * Stellt verschiedene Cashflowberechnungsverfahren zur Verfügung
 */
public final class CFAlgorithms {

	private CFAlgorithms() {
	}

	/**
	 * @return Cashflow Algorithmus nach dem APV Verfahren
	 */
	public static CFAlgorithm<APVResult> getApvAlgorithm() {
		return CFConfig.getApvAlgorithm();
	}

	/**
	 * @return Cashflow Algorithmus nach dem Entity Verfahren
	 */
	public static CFAlgorithm<EntityResult> getEntityAlgorithm() {
		return CFConfig.getEntityAlgorithm();
	}

	/**
	 * @return Cashflow Algorithmus nach dem Equity Verfahren
	 */
	public static CFAlgorithm<EquityResult> getEquityAlgorithm() {
		return CFConfig.getEquityAlgorithm();
	}

	/**
	 * @return Überschussermittlung nach dem Entity Verfahren
	 */
	public static UeberschussAlgorithm getEntityUeberschussAlgorithm() {
		return CFConfig.getEntityUeberschussAlgorithm();
	}
	/**
	 * @return Überschussermittlung nach dem equity Verfahren
	 */
	public static UeberschussAlgorithm getEquityUeberschussAlgorithm() {
		return CFConfig.getEquityUeberschussAlgorithm();
	}

}
