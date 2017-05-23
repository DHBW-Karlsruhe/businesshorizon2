package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithms;
import dhbw.ka.mwi.businesshorizon2.cf.CFConfig;
import dhbw.ka.mwi.businesshorizon2.cf.equity.EquityResult;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Die Ergebnisse werden erst beim Aufruf der Methoden ermittelt. Diese Klasse ohne Caching ist durch die rekursive Natur zu langsam und sollte nicht verwendet werden
 *
 */
class LazyEntityResult implements EntityResult {

	private final CFParameter parameter;

	private final EquityResult equity;

	LazyEntityResult(final CFParameter parameter) {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter must not be null");
		}
		this.parameter = parameter;
		equity = CFAlgorithms.getEquityAlgorithm().calculate(parameter);
	}

    double getPersSteuer(final int periode) {
		return 0.5d * CFAlgorithms.getEntityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				* parameter.getPerSteuer();
	}

	double getUeberschussGroesse(final int periode) {
		return CFAlgorithms.getEntityUeberschussAlgorithm().getUberschussGroesse(parameter, periode)
				- getPersSteuer(periode);
	}

	double getMarktwertFK(final int periode) {
		return parameter.getBilanz(periode).getZinsPfPassiva();
	}

	double getGewichtEK(final int periode, final int depth) {
		return getUnternehmenswert(periode, depth + 1)
				/ (getUnternehmenswert(periode, depth + 1) + getMarktwertFK(periode));
	}

	double getGewichtetFK(final int periode, final int depth) {
		return getMarktwertFK(periode) / (getMarktwertFK(periode) + getUnternehmenswert(periode, depth + 1));
	}

	double getEKKostVersch(final int periode) {
		return equity.getEKKostVersch(periode);
	}

	double getFKKost() {
		return parameter.getZinsaufwand() * (1 - parameter.getErtragsSteuer()) * (1 - 0.5 * parameter.getPerSteuer());
	}

	@Override
	public double getWACC(final int periode) {
		return getWACC(periode, 0);
	}

	double getWACC(final int periode, final int depth) {
		return getEKKostVersch(periode) * getGewichtEK(periode, depth) + getFKKost() * getGewichtetFK(periode, depth);
	}

	double getMarktwertGesamtK(final int periode, final int depth) {
		if (parameter.isLastPeriode(periode)) {
			return getUeberschussGroesse(periode + 1) / getWACC(periode, depth);
		}

		return (getUeberschussGroesse(periode + 1) + getMarktwertGesamtK(periode + 1, depth))
				/ (1 + getWACC(periode, depth));
	}

	double getUnternehmenswert(final int periode, final int depth) {
		if (depth >= CFConfig.getMaxDepth()) {
			return 1d;
		}

		return getMarktwertGesamtK(periode, depth) - getMarktwertFK(periode);
	}

	@Override
	public double getUnternehmenswert(final int periode) {
		return getUnternehmenswert(periode, 0);
	}

}
