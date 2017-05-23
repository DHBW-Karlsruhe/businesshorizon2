package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class ARPredictorRunner {

	private final ARPredictor predictor;

	public ARPredictorRunner(final ARPredictor predictor) {
		this.predictor = predictor;
	}

	private static SlidingWindow fillSlidingWindowFromTimeSeries(final double[] timeSeries, final int size, final double avg){
		final SlidingWindow lastValues = new SlidingWindow(size);

		for (int i = timeSeries.length - 1 - size; i < timeSeries.length; i++) {
			lastValues.put(timeSeries[i] - avg);
		}

		return lastValues;
	}

	public double[] runPredictions(final double[] timeSeries, final double[] coefficients, final int numPeriods) {
		final double[] result = new double[numPeriods];

		final double stdDev = new StandardDeviation(false).evaluate(timeSeries);
		final double avg = new Mean().evaluate(timeSeries);

		final SlidingWindow lastValues = fillSlidingWindowFromTimeSeries(timeSeries, coefficients.length, avg);

		for (int i = 0; i < numPeriods; i++) {
			result[i] = predictor.predict(lastValues.getData(), coefficients, stdDev, avg);
			lastValues.put(result[i] - avg);
		}

		return result;
	}

}
