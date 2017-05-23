package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.apv.APVResult;
import dhbw.ka.mwi.businesshorizon2.cf.apv.LazyAPVAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.entity.EntityResult;
import dhbw.ka.mwi.businesshorizon2.cf.entity.EntityUeberschuss;
import dhbw.ka.mwi.businesshorizon2.cf.entity.LazyEntityAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.equity.EquityResult;
import dhbw.ka.mwi.businesshorizon2.cf.equity.EquityUeberschuss;
import dhbw.ka.mwi.businesshorizon2.cf.equity.LazyEquityAlgorithm;

/**
 * Erlaubt es verschiedene Parameter der Cashflow Berechnung anzupassen
 *
 *
 */
public final class CFConfig {

	private static int maxDepth = 100;

	private static CFAlgorithm<APVResult> apvAlgorithm = new LazyAPVAlgorithm();
	private static CFAlgorithm<EntityResult> entityAlgorithm = new LazyEntityAlgorithm();
	private static CFAlgorithm<EquityResult> equityAlgorithm = new LazyEquityAlgorithm();

	private static UeberschussAlgorithm entityUeberschussAlgorithm = new EntityUeberschuss();
	private static UeberschussAlgorithm equityUeberschussAlgorithm = new EquityUeberschuss();

	private CFConfig() {
	}

	/**
	 * @return Die maximale Tiefe wie weit Rekursiv gerechnet werden soll. Desto
	 *         größer dieser Wert ist, desto genauer sind die Ergebnisse und
	 *         desto länger dauert die Ermittlung
	 */
	public static int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * @param maxDepth Die maximale Tiefe wie weit Rekursiv gerechnet werden soll. Desto
	 *         größer dieser Wert ist, desto genauer sind die Ergebnisse und
	 *         desto länger dauert die Ermittlung
	 */
	public static void setMaxDepth(final int maxDepth) {
		CFConfig.maxDepth = maxDepth;
	}

	/**
	 * @return Der Algorithmus, der als APV Verfahren verwendet wird
	 * @see CFAlgorithms#getApvAlgorithm() Zum Verwenden der Algorithmen sollte {@link CFAlgorithms#getApvAlgorithm()} aufgerufen werden
	 *
	 */
	public static CFAlgorithm<APVResult> getApvAlgorithm() {
		return apvAlgorithm;
	}

	/**
	 * @param apvAlgorithm Setzt den APV Algorithmus für alle Berechnungen
	 */
	public static void setApvAlgorithm(final CFAlgorithm<APVResult> apvAlgorithm) {
		CFConfig.apvAlgorithm = apvAlgorithm;
	}

	/**
	 * @return Der Algorithmus, der als Entity Verfahren verwendet wird
	 * @see CFAlgorithms#getEntityAlgorithm() Zum Verwenden der Algorithmen sollte {@link CFAlgorithms#getEntityAlgorithm()} aufgerufen werden
	 */
	public static CFAlgorithm<EntityResult> getEntityAlgorithm() {
		return entityAlgorithm;
	}


	/**
	 * @param entityAlgorithm Setzt den Entity Algorithmus für alle Berechnungen
	 */
	public static void setEntityAlgorithm(final CFAlgorithm<EntityResult> entityAlgorithm) {
		CFConfig.entityAlgorithm = entityAlgorithm;
	}
	/**
	 * @return Der Algorithmus, der als Equity Verfahren verwendet wird
	 * @see CFAlgorithms#getEquityAlgorithm() Zum Verwenden der Algorithmen sollte {@link CFAlgorithms#getEquityAlgorithm()} aufgerufen werden
	 */
	public static CFAlgorithm<EquityResult> getEquityAlgorithm() {
		return equityAlgorithm;
	}

	/**
	 * @param equityAlgorithm Setzt den Equity Algorithmus für alle Berechnungen
	 */
	public static void setEquityAlgorithm(final CFAlgorithm<EquityResult> equityAlgorithm) {
		CFConfig.equityAlgorithm = equityAlgorithm;
	}

	/**
	 *
	 * @return Der Algorithmus, der zur Überschussermitllung nach dem Entity Verfahren verwendet wird
	 * @see CFAlgorithms#getEntityUeberschussAlgorithm() Zum Verwenden der Algorithmen sollte {@link CFAlgorithms#getEntityUeberschussAlgorithm()} aufgerufen werden
	 */
	public static UeberschussAlgorithm getEntityUeberschussAlgorithm() {
		return entityUeberschussAlgorithm;
	}

	/**
	 * @param entityUeberschussAlgorithm Setzt den Entity Überschussermittlungsalgorithmus für alle Berechnungen
	 */
	public static void setEntityUeberschussAlgorithm(final UeberschussAlgorithm entityUeberschussAlgorithm) {
		CFConfig.entityUeberschussAlgorithm = entityUeberschussAlgorithm;
	}

	/**
	 * @return Der Algorithmus, der zur Überschussermitllung nach dem Equity Verfahren verwendet wird
	 * @see CFAlgorithms#getEquityUeberschussAlgorithm() Zum Verwenden der Algorithmen sollte {@link CFAlgorithms#getEquityUeberschussAlgorithm()} aufgerufen werden
	 */
	public static UeberschussAlgorithm getEquityUeberschussAlgorithm() {
		return equityUeberschussAlgorithm;
	}

	/**
	 * @param equityUeberschussAlgorithm Setzt den Equity Überschussermittlungsalgorithmus für alle Berechnungen
	 */
	public static void setEquityUeberschussAlgorithm(final UeberschussAlgorithm equityUeberschussAlgorithm) {
		CFConfig.equityUeberschussAlgorithm = equityUeberschussAlgorithm;
	}

}
