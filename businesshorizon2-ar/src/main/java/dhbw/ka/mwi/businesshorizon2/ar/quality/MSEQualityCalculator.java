package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictor;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.Arrays;

/**
 * Berechnet die Güte eines AR-Modells.
 */
public class MSEQualityCalculator implements QualityCalculator {

    @Override
    public double calculateQuality(final ARModel model) {
        double error = 0;
        final double avg = new Mean().evaluate(model.getTimeSeries());
        final double[] predictions = predict(model);
        for (int i = 0; i < predictions.length; i++) {
            error += Math.pow(model.getTimeSeries()[i + model.getCoefficients().length] - predictions[i], 2);
        }
        return Math.sqrt(error / predictions.length) / avg;
    }

    /**
     * Prognostiziert die Werte einer Zeitreihe, um sie mit den realen Werten der Zeitreihe zu vergleichen
     * @param model Das AR-Modell der Zeitreihe
     * @return Alle Prognosen ab dem Zeitpunkt p+1, da immer p Werte für die Prognose gebraucht werden
     */
    private static double[] predict(final ARModel model) {
        final double avg = new Mean().evaluate(model.getTimeSeries());
        final double[] predictions = new double[model.getTimeSeries().length - model.getCoefficients().length];
        final ARPredictor predictor = new ARPredictor();
        for (int i = 0; i < model.getTimeSeries().length - model.getCoefficients().length; i++) {
            predictions[i] = predictor.predict(Arrays.copyOfRange(model.getTimeSeries(), i, i + model.getCoefficients().length), model.getCoefficients(), avg);
        }
        return predictions;
    }

}
