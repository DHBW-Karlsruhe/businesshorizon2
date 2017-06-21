package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Random;

/**
 * Eine Klasse, die den nächsten Wert einer Zeitreihe prognostiziert
 */
public class ARPredictor {

	private final Random random;

	public ARPredictor() {
		this(new Random());
	}

	ARPredictor(final Random random) {
		this.random = random;
	}

    /**
     * Berechnet einen zukünftigen Wert der Zeitreihe
     * @param centeredValues Die zentrierten Werte einer Zeitreihe
     * @param coefficients Die Koeffizeinten der AR-Modellgleichung
     * @param stdDev Die Standardabweichung der Zeitreihe
     * @param avg Der Mittelwert der Zeitreihe
     * @return Die Prognose des nächsten Wertes der Zeitreihe
     */
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
