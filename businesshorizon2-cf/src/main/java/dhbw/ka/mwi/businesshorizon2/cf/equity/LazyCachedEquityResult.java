package dhbw.ka.mwi.businesshorizon2.cf.equity;

import dhbw.ka.mwi.businesshorizon2.cf.MethodMap;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Stellt Caching für LazyEquityResult zur Verfügung
 *
 */
class LazyCachedEquityResult extends LazyEquityResult {

	private final MethodMap<EquityMethods> cache = new MethodMap<>();

	LazyCachedEquityResult(final CFParameter parameter) {
		super(parameter);
	}

    @Override
	double getPersSteuer(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getPersSteuer, periode),
				arg0 -> super.getPersSteuer(periode));
	}

	@Override
	double getKorrekturUeber(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getKorrekturUeber, periode),
				arg0 -> super.getKorrekturUeber(periode));
	}

	@Override
	double getUeberschussGroesse(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getUeberschussGroesse, periode),
				arg0 -> super.getUeberschussGroesse(periode));
	}

	@Override
	double getVerschuldungsgrad(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getVerschuldungsgrad, periode, depth),
				arg0 -> super.getVerschuldungsgrad(periode, depth));
	}

	@Override
	double getEKKostVersch(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getEKKostVersch, periode, depth),
				arg0 -> super.getEKKostVersch(periode, depth));
	}

	@Override
	double getUnternehmenswert(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EquityMethods.getUnternehmenswert, periode, depth),
				arg0 -> super.getUnternehmenswert(periode, depth));
	}

}
