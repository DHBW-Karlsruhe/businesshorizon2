package dhbw.ka.mwi.businesshorizon2.ar.model;

import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictorRunner;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.RandomWalk;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.RandomWalkPredictor;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class ARModel {

    private final double[] coefficients;
    private final double[] timeSeries;

    public ARModel(final double[] coefficients, final double[] timeSeries) {
        this.coefficients = coefficients;
        this.timeSeries = timeSeries;
    }

    /**
     * Prognostizert die zukünftigen Werte der Zeitreihe mithilfe der @{@link ARPredictorRunner}- und der der @{@link RandomWalkPredictor}-Klasse.
     * @param numPeriods Die Anzahl an zukünftigen Zeitpunkten, die prognostiziert werden.
     * @return Die prognostizierten Werte der Zeitreihe.
     */
    public double[] predict(final int numPeriods) {
        //Die Formel für die Prognose befindet sich in Schlittgen, Rainer & Streitberg, Bernd (2001). Zeitreihenanalyse (9. Aufl.) Seite 121
        final double stdDev = new StandardDeviation(false).evaluate(timeSeries);
        return new ARPredictorRunner(new RandomWalkPredictor(new RandomWalk(stdDev))).runPredictions(timeSeries, coefficients, numPeriods);
    }

    /**
     * Gibt die Koeffizienten der AR-Model Gleichung zurück.
     */
    public double[] getCoefficients() {
        return coefficients;
    }

    public double[] getTimeSeries() {
        return timeSeries;
    }
}

