package dhbw.ka.mwi.businesshorizon2.ar.predictor;

/**
 * Eine Klasse, die den nächsten Wert einer Zeitreihe prognostiziert
 */
public class ARPredictor {

    /**
     * Berechnet einen zukünftigen Wert der Zeitreihe
     *
     * @param timeSeries   Die Zeitreihe
     * @param coefficients Die Koeffizeinten der AR-Modellgleichung
     * @param avg          Der Mittelwert der Zeitreihe
     * @return Die Prognose des nächsten Wertes der Zeitreihe
     */

    public double predict(final double[] timeSeries, final double[] coefficients, final double avg) {
        if (timeSeries.length != coefficients.length) {
            throw new IllegalArgumentException("Length of values and length of coeffs does not match");
        }
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * (timeSeries[i] - avg);
        }

        return result + avg;
    }

}
