package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.GUVCalculator;
import dhbw.ka.mwi.businesshorizon2.cf.UeberschussAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Ermittelt die Entityüberschussgröße, indem die Steuern, Abschreibungen und Investitionen vom EBIT abgezogen werden
 *
 */
public class EntityUeberschuss2 implements UeberschussAlgorithm {

	private static double getEBIT(final CFParameter parameter, final int periode) {
		return GUVCalculator.getEBIT(parameter, periode);
	}

	private static double getRealSteuer(final CFParameter parameter, final int periode) {
		return -GUVCalculator.getErtragssteuer(parameter, periode);
	}

	private static double getZinsSteuer(final CFParameter parameter, final int periode) {
		return -parameter.getErtragsSteuer() * GUVCalculator.getZinsaufwand(parameter, periode);
	}

	private static double getAbschreibungen(final CFParameter parameter, final int periode) {
		return parameter.getGuv(periode).getAbschr();
	}

	private static double getInvestAnlage(final CFParameter parameter, final int periode) {
		return -(parameter.getBilanz(periode).getAnlage() - parameter.getBilanz(periode - 1).getAnlage()
				+ parameter.getGuv(periode).getAbschr());
	}

	private static double getInvestWorking(final CFParameter parameter, final int periode) {
		return -(parameter.getBilanz(periode).getUmlauf() - parameter.getBilanz(periode - 1).getUmlauf())
				+ (parameter.getBilanz(periode).getSonstPassiva() - parameter.getBilanz(periode - 1).getSonstPassiva());
	}

	@Override
	public double getUberschussGroesse(final CFParameter parameter, final int periode) {
		return getEBIT(parameter, periode) + getRealSteuer(parameter, periode) + getZinsSteuer(parameter, periode)
				+ getAbschreibungen(parameter, periode) + getInvestAnlage(parameter, periode)
				+ getInvestWorking(parameter, periode);
	}
}
