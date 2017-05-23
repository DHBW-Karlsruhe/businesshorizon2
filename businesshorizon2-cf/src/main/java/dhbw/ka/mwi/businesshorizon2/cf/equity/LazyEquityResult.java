package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithms;
import dhbw.ka.mwi.businesshorizon2.cf.CFConfig;
import dhbw.ka.mwi.businesshorizon2.cf.apv.APVResult;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
/**
 * Die Ergebnisse werden erst beim Aufruf der Methoden ermittelt. Diese Klasse ohne Caching ist durch die rekursive Natur zu langsam und sollte nicht verwendet werden
 *
 */
class LazyEquityResult implements EquityResult {

	private final CFParameter parameter;
	private final APVResult apv;

	LazyEquityResult(final CFParameter parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter must not be null");
		}
		this.parameter = parameter;
		apv = CFAlgorithms.getApvAlgorithm().calculate(parameter);
	}

    double getPersSteuer(final int periode) {
		return 0.5d * CFAlgorithms.getEquityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				* parameter.getPerSteuer();
	}

	double getKorrekturUeber(final int periode) {
		return 0.5 * parameter.getPerSteuer() * (parameter.getBilanz(periode - 1).getZinsPfPassiva()
				- parameter.getBilanz(periode).getZinsPfPassiva());
	}

	double getUeberschussGroesse(final int periode) {
		return CFAlgorithms.getEquityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				- getPersSteuer(periode) - getKorrekturUeber(periode);
	}

	double getVerschuldungsgrad(final int periode, final int depth) {
		return apv.getMarktwertFK(periode) / getUnternehmenswert(periode, depth + 1);
	}

	double getEKKostVersch(final int periode, final int depth) {
		if (parameter.isLastPeriode(periode)) {
			return parameter.getEKKostenUnverschuldet()
					+ (parameter.getEkKosten() - parameter.getZinsaufwand()) * (1 - parameter.getErtragsSteuer())
							* (1 - 0.5 * parameter.getPerSteuer()) * parameter.getBilanz(periode).getZinsPfPassiva()
							/ getUnternehmenswert(periode, depth + 1)
					- parameter.getEKKostenUnverschuldet() * parameter.getErtragsSteuer() * 0.5
							* parameter.getPerSteuer() / (1 - parameter.getPerSteuer())
							* parameter.getBilanz(periode).getZinsPfPassiva() / getUnternehmenswert(periode, depth + 1);
		}

		return parameter.getEKKostenUnverschuldet()
				+ (parameter.getEKKostenUnverschuldet() - parameter.getZinsaufwand()
						* (1 - parameter.getErtragsSteuer()) * (1 - 0.5 * parameter.getPerSteuer()))
						* parameter.getBilanz(periode).getZinsPfPassiva() / getUnternehmenswert(periode, depth + 1)
				+ (apv.getTaxShield(periode + 1)
						- apv.getTaxShield(periode) * (1 + parameter.getEKKostenUnverschuldet()))
						/ getUnternehmenswert(periode, depth + 1);
	}

	double getUnternehmenswert(final int periode, final int depth) {
		if (depth >= CFConfig.getMaxDepth()) {
			return 1d;
		}
		if (parameter.isLastPeriode(periode)) {
			return getUeberschussGroesse(periode + 1) / getEKKostVersch(periode, depth);
		}
		return (getUeberschussGroesse(periode + 1) + getUnternehmenswert(periode + 1, 0))
				/ (1 + getEKKostVersch(periode, depth));
	}

	@Override
	public double getUnternehmenswert(final int periode) {
		return getUnternehmenswert(periode, 0);

	}

	@Override
	public double getEKKostVersch(final int periode) {
		return getEKKostVersch(periode, 0);
	}
}
