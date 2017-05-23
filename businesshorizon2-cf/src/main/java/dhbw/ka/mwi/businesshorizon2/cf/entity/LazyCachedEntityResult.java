package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizon2.cf.MethodMap;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

/**
 * Stellt Caching für LazyEntityResults zur Verfügung
 *
 */
class LazyCachedEntityResult extends LazyEntityResult {

	private final MethodMap<EntityMethods> cache = new MethodMap<>();

	LazyCachedEntityResult(final CFParameter parameter) {
		super(parameter);
	}

    @Override
	double getPersSteuer(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getPersSteuer, periode),
				arg0 -> super.getPersSteuer(periode));
	}

	@Override
	double getUeberschussGroesse(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getUeberschussGroesse, periode),
				arg0 -> super.getUeberschussGroesse(periode));
	}

	@Override
	double getMarktwertFK(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getMarktwertFK, periode),
				arg0 -> super.getMarktwertFK(periode));
	}

	@Override
	double getGewichtEK(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getGewichtEK, periode, depth),
				arg0 -> super.getGewichtEK(periode, depth));
	}

	@Override
	double getGewichtetFK(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getGewichtetFK, periode, depth),
				arg0 -> super.getGewichtetFK(periode, depth));
	}

	@Override
	double getEKKostVersch(final int periode) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getEKKostVersch, periode),
				arg0 -> super.getEKKostVersch(periode));
	}

	@Override
	double getFKKost() {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getFKKost), arg0 -> super.getFKKost());
	}

	@Override
	double getWACC(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getWACC, periode, depth),
				arg0 -> super.getWACC(periode, depth));
	}

	@Override
	double getMarktwertGesamtK(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getMarktwertGesamtK, periode, depth),
				arg0 -> super.getMarktwertGesamtK(periode, depth));
	}

	@Override
	double getUnternehmenswert(final int periode, final int depth) {
		return cache.computeIfAbsent(cache.getMethodKey(EntityMethods.getUnternehmenswert, periode, depth),
				arg0 -> super.getUnternehmenswert(periode, depth));
	}
}
