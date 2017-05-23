package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Stell ein allgemeines Verfahren zur Verfügung um eine Periodenüberschussgröße zu ermitteln
 *
 */
@FunctionalInterface
public interface UeberschussAlgorithm {
	/**
	 * @param parameter Die Parameter
	 * @param periode Die Periode
	 * @return Die Überschussgröße für die angegebenen Parameter und die Periode
	 */
    double getUberschussGroesse(CFParameter parameter, int periode);
}
