package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.GUVCalculator;
import dhbw.ka.mwi.businesshorizon2.cf.UeberschussAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
/**
 * Ermittelt die Equityüberschussgröße, indem die Thesaurierung vom Jahresüberschuss abgezogen wird
 *
 */
public class EquityUeberschuss implements UeberschussAlgorithm {


	private static double getJahresUeberschuss(final CFParameter parameter, final int periode) {
		return GUVCalculator.getJahresUeberschuss(parameter, periode);
	}

	private static double getThesaurierung(final CFParameter parameter, final int periode) {
		return parameter.getBilanz(periode).getEk() - parameter.getBilanz(periode - 1).getEk();
	}

	@Override
	public double getUberschussGroesse(final CFParameter parameter, final int periode) {
		return getJahresUeberschuss(parameter, periode) - getThesaurierung(parameter, periode);
	}
}
