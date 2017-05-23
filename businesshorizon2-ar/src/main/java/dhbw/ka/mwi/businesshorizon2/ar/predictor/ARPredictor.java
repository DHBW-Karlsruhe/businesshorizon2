package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Random;

public class ARPredictor {

	private final Random random;

	public ARPredictor() {
		this(new Random());
	}

	ARPredictor(final Random random) {
		this.random = random;
	}

	public double predict(final double[] centeredValues, final double[] coefficients, final double stdDev, final double avg) {
		if (centeredValues.length != coefficients.length) {
			throw new IllegalArgumentException("Length of values and length of coeffs does not match");
		}

		double result = 0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * centeredValues[i];
		}

		return result + stdDev * (random.nextBoolean() ? 1 : -1) + avg;

	}

}
