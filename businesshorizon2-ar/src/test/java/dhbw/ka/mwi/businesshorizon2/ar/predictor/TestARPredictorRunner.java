package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class TestARPredictorRunner {

	@Test
	public void test() {
		final double[] timeSeries = { 77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d,
				79644d };
		final double[] coeffs = { -0.21244, -0.79116 };
		final double[] predictions = new ARPredictorRunner(new ARPredictor(new Random(1))).runPredictions(timeSeries, coeffs, 2);
		assertArrayEquals(new double[]{75584.95,73541.64}, predictions,0.01);
	}
}
