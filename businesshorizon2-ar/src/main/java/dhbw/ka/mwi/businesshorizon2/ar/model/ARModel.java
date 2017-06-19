package dhbw.ka.mwi.businesshorizon2.ar.model;

import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictor;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictorRunner;

public class ARModel {

    /**
     * Koeffizienten der AR-Model Gleichung
     */
    private final double[] coefficients;
    private final double[] timeSeries;

    public ARModel(final double[] coefficients, final double[] timeSeries) {
        this.coefficients = coefficients;
        this.timeSeries = timeSeries;
    }

    /**
     * Prognostizert die zukünftigen Werte der Zeitreihe
     * @param numPeriods Die Anzahl an zukünftigen Zeitpunkten, die prognostiziert werden
     * @return Die prognostizierten Werte der Zeitreihe
     */
    public double[] predict(final int numPeriods) {
        return new ARPredictorRunner(new ARPredictor()).runPredictions(timeSeries, coefficients, numPeriods);
    }

    public double[] getCoefficients() {
        return coefficients;
    }
}
