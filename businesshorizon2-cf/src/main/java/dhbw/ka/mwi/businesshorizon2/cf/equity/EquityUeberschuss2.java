package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.GUVCalculator;
import dhbw.ka.mwi.businesshorizon2.cf.UeberschussAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
/**
 * Ermittelt die Equityüberschussgröße, indem die Abschreibungen und Investitionen vom Jahresüberschuss abgezogen wird
 *
 */
public class EquityUeberschuss2 implements UeberschussAlgorithm {


	private static double getJahresUeberschuss(final CFParameter parameter, final int periode) {
		return GUVCalculator.getJahresUeberschuss(parameter, periode);
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

	private static double getFremdkapital(final CFParameter parameter, final int periode) {
		return parameter.getBilanz(periode).getZinsPfPassiva() - parameter.getBilanz(periode - 1).getZinsPfPassiva();
	}

	@Override
	public double getUberschussGroesse(final CFParameter parameter, final int periode) {
		return getJahresUeberschuss(parameter, periode) + getAbschreibungen(parameter, periode)
				+ getInvestAnlage(parameter, periode) + getInvestWorking(parameter, periode)
				+ getFremdkapital(parameter, periode);
	}
}
