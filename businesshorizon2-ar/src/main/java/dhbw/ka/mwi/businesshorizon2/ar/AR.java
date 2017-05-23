package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;

public final class AR {

	private AR() {
	}

	public static ARModel getModel(final double[] timeSeries, final int ps){
		return ARConfig.getModelCalculator().getModel(timeSeries, ps);
	}

	public static double[] predict(final double[] timeSeries, final int p, final int numPeriods) {
		return getModel(timeSeries,p).predict(numPeriods);
	}

}
