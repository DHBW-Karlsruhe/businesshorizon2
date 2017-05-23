package dhbw.ka.mwi.businesshorizon2.ar.model;

import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictor;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictorRunner;

public class ARModel {

    private final double[] coefficients;
    private final double[] timeSeries;

    public ARModel(final double[] coefficients, final double[] timeSeries) {
        this.coefficients = coefficients;
        this.timeSeries = timeSeries;
    }

    public double[] predict(final int numPeriods) {
        return new ARPredictorRunner(new ARPredictor()).runPredictions(timeSeries, coefficients, numPeriods);
    }

    public double[] getCoefficients() {
        return coefficients;
    }
}
