package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

/**
 * Beinhaltet in einem {@link SlidingWindow} stets die Werte einer Zeitreihe,
 * die für die nächste Prognose relevant sind
 * und berechnet anhand des {@link ARPredictor}s die nächsten Werte der Zeitreihe
 */
public class ARPredictorRunner {

    private final ARPredictor predictor;

    public ARPredictorRunner(final ARPredictor predictor) {
        this.predictor = predictor;
    }

    /**
     * Sagt anhand des Predictors zukünftige Werte der Zeitreihe voraus
     *
     * @param timeSeries   Die betrachtete Zeitreihe
     * @param coefficients Die Koeffizeinten der AR-Modellgleichung
     * @param numPeriods   Die Anzahl an zukünftigen Zeitpunkten, die prognostiziert werden
     * @return Die prognostizierten Werte der Zeitreihe
     */
    public double[] runPredictions(final double[] timeSeries, final double[] coefficients, final int numPeriods) {
        final double[] result = new double[numPeriods];

        final double stdDev = new StandardDeviation(false).evaluate(timeSeries);
        final double avg = new Mean().evaluate(timeSeries);

        // Erzeugt einen SlidingWindow mit den zentrierten Werten der Zeitreihe
        // lastValues enthält immer die Werte, die für die nächste Prognose wichtig sind.
        final SlidingWindow lastValues = fillSlidingWindowFromTimeSeries(timeSeries, coefficients.length);

        for (int i = 0; i < numPeriods; i++) {
            result[i] = predictor.predict(lastValues.getData(), coefficients, stdDev, avg);
            lastValues.put(result[i]);
        }

        return result;
    }

    private static SlidingWindow fillSlidingWindowFromTimeSeries(final double[] timeSeries, final int size) {
        final SlidingWindow lastValues = new SlidingWindow(size);

        // Zentrieren der Zeitreihe
        for (int i = timeSeries.length - 1 - size; i < timeSeries.length; i++) {
            lastValues.put(timeSeries[i]);
        }

        return lastValues;
    }

}
