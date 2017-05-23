package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.GUVCalculator;
import dhbw.ka.mwi.businesshorizon2.cf.UeberschussAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Ermittelt die Entityüberschussgröße, indem die Steuern und die Thesaurierung vom EBIT abgezogen werden
 *
 */
public class EntityUeberschuss implements UeberschussAlgorithm {

	private static double getEBIT(final CFParameter parameter, final int periode) {
		return GUVCalculator.getEBIT(parameter, periode);
	}

	private static double getRealSteuer(final CFParameter parameter, final int periode) {
		return -GUVCalculator.getErtragssteuer(parameter, periode);
	}

	private static double getZinsSteuer(final CFParameter parameter, final int periode) {
		return -parameter.getErtragsSteuer() * GUVCalculator.getZinsaufwand(parameter, periode);
	}

	private static double getThesaurierung(final CFParameter parameter, final int periode) {
		return parameter.getBilanz(periode).getEk() - parameter.getBilanz(periode - 1).getEk()
				+ parameter.getBilanz(periode).getZinsPfPassiva() - parameter.getBilanz(periode - 1).getZinsPfPassiva();
	}

	@Override
	public double getUberschussGroesse(final CFParameter parameter, final int periode) {
		return getEBIT(parameter, periode) + getRealSteuer(parameter, periode) + getZinsSteuer(parameter, periode)
				- getThesaurierung(parameter, periode);
	}
}
