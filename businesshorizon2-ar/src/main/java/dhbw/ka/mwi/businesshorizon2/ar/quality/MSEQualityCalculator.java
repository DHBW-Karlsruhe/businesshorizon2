package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class MSEQualityCalculator implements QualityCalculator {

    /**
     * Berechnet die Güte eines AR-Modells
     * Also wie gut das AR-Modell auf die Zeitreihe passt
     * Dafür wird der durschnittliche quadratische Fehler betrachtet
     * @param model Das AR-Modell
     * @return Die Güte
     */
    @Override
    public double calculateQuality(final ARModel model) {
        double error = 0;
        final double avg = new Mean().evaluate(model.getTimeSeries());
        final double[] predictions = QualityPredictor.predict(model);
        for (int i = 0; i < predictions.length; i++) {
            error += Math.pow(model.getTimeSeries()[i + model.getCoefficients().length] - predictions[i], 2);
        }
        return Math.sqrt(error / predictions.length) / avg;
    }

}
