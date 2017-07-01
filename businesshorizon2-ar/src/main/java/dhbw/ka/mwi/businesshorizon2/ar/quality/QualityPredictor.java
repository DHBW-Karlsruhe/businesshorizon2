package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictor;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.Arrays;

final class QualityPredictor {
    private QualityPredictor() {
    }

    static double[] predict(final ARModel model) {
        final double avg = new Mean().evaluate(model.getTimeSeries());
        final double[] predictions = new double[model.getTimeSeries().length - model.getCoefficients().length];
        final ARPredictor predictor = new ARPredictor();
        for (int i = 0; i < model.getTimeSeries().length - model.getCoefficients().length; i++) {
            predictions[i] = predictor.predict(Arrays.copyOfRange(model.getTimeSeries(), i, i + model.getCoefficients().length), model.getCoefficients(), avg);
        }
        return predictions;
    }
}
