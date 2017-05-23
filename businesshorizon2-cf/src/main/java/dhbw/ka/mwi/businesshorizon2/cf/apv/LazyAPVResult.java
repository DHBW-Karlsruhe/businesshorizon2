package dhbw.ka.mwi.businesshorizon2.cf.apv;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithms;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Die Ergebnisse werden erst beim Aufruf der Methoden ermittelt
 *
 */
class LazyAPVResult implements APVResult {

	private final CFParameter parameter;

	LazyAPVResult(final CFParameter parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter must not be null");
		}
		this.parameter = parameter;
	}

	private double getPersSteuer(final int periode) {
		return 0.5d * CFAlgorithms.getEntityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				* parameter.getPerSteuer();
	}

	private double getUeberschussGroesse(final int periode) {
		return CFAlgorithms.getEntityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				- getPersSteuer(periode);
	}

	private double getZinsaufwand(final int periode) {
		return parameter.getBilanz(periode).getZinsPfPassiva() * parameter.getZinsaufwand();
	}

	private double getSteuerKapStruktur(final int periode) {
		return parameter.getErtragsSteuer() * getZinsaufwand(periode - 1) * (1 - 0.5 * parameter.getPerSteuer());
	}

	private double getSteuerAusschuettung(final int periode) {
		return (parameter.getBilanz(periode - 1).getZinsPfPassiva() - parameter.getBilanz(periode).getZinsPfPassiva())
				* 0.5 * parameter.getPerSteuer();
	}

	@Override
	public double getTaxShield(final int periode) {
		if (parameter.isLastPeriode(periode)) {
			return (getSteuerKapStruktur(periode + 1) + getSteuerAusschuettung(periode + 1))
					/ (parameter.getZinsaufwand() * (1 - parameter.getPerSteuer()));
		}

		return (getSteuerKapStruktur(periode + 1) + getSteuerAusschuettung(periode + 1) + getTaxShield(periode + 1))
				/ (1d + parameter.getZinsaufwand() * (1d - parameter.getPerSteuer()));
	}

	private double getMarktwertEK(final int periode) {
		if (parameter.isLastPeriode(periode)) {
			return getUeberschussGroesse(periode + 1) / parameter.getEKKostenUnverschuldet();
		}

		return (getUeberschussGroesse(periode + 1) + getMarktwertEK(periode + 1))
				/ (1 + parameter.getEKKostenUnverschuldet());
	}

	@Override
	public double getMarktwertFK(final int periode) {
		return parameter.getBilanz(periode).getZinsPfPassiva();
	}

	@Override
	public double getUnternehmenswert(final int periode) {
		return getTaxShield(periode) + getMarktwertEK(periode) - getMarktwertFK(periode);
	}

}
