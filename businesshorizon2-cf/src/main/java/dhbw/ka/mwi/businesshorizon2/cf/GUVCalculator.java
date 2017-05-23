package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Stellt Allgemeine Methoden zur Ermittlung von verschiedenen GUV Werten zur Verfügung
 *
 */
public final class GUVCalculator {

	private GUVCalculator() {
	}

	public static double getEBITDA(final CFParameter parameter, final int periode) {
		return parameter.getGuv(periode).getGesamtleistung() - parameter.getGuv(periode).getOpkosten();
	}

	public static double getEBIT(final CFParameter parameter, final int periode) {
		return getEBITDA(parameter, periode) - parameter.getGuv(periode).getAbschr();
	}

	public static double getZinsaufwand(final CFParameter parameter, final int periode) {
		return parameter.getBilanz(periode - 1).getZinsPfPassiva() * parameter.getZinsaufwand();
	}

	public static double getEBT(final CFParameter parameter, final int periode) {
		return getEBIT(parameter, periode) - getZinsaufwand(parameter, periode);
	}

	public static double getErtragssteuer(final CFParameter parameter, final int periode) {
		return parameter.getJahresUeberschuss()
				* (getEBT(parameter, periode) + 0.5 * getZinsaufwand(parameter, periode))
				+ (getEBT(parameter, periode) - parameter.getJahresUeberschuss()
						* (getEBT(parameter, periode) + 0.5 * getZinsaufwand(parameter, periode)))
						* parameter.getStrukturbilanzen();
	}

	public static double getJahresUeberschuss(final CFParameter parameter, final int periode) {
		return getEBT(parameter, periode) - getErtragssteuer(parameter, periode);
	}
}
