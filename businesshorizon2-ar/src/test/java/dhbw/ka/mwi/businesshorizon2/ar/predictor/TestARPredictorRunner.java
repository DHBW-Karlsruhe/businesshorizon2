package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.junit.Test;

import java.util.Random;

import static dhbw.ka.mwi.businesshorizon2.ar.AssertRelative.assertRelative;

public class TestARPredictorRunner {

    @Test
    public void test() {
        final double[] timeSeries = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d,
                79644d};
        final double[] coeffs = {-0.21244, -0.79116};

        final double[] predictions = new ARPredictorRunner(new ARPredictor()).runPredictions(timeSeries, coeffs, 2);
        assertRelative(new double[]{73383.35, 77485.07}, predictions);
    }

    @Test
    public void testRandomWalk() {
        final double[] timeSeries = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d,
                79644d};
        final double[] coeffs = {-0.21244, -0.79116};

        final double stdDev = new StandardDeviation(false).evaluate(timeSeries);
        final double[] predictions = new ARPredictorRunner(new RandomWalkPredictor(new RandomWalk(new Random(1), stdDev))).runPredictions(timeSeries, coeffs, 2);
        assertRelative(new double[]{75584.95, 73541.64}, predictions);
    }
}
