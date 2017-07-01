package dhbw.ka.mwi.businesshorizon2.ar.predictor;

public class RandomWalkPredictor extends ARPredictor {

    private final RandomWalk randomWalk;

    public RandomWalkPredictor(final RandomWalk randomWalk) {
        this.randomWalk = randomWalk;
    }

    @Override
    public double predict(final double[] timeSeries, final double[] coefficients, final double avg) {
        return super.predict(timeSeries, coefficients, avg) + randomWalk.getRandomWalk();
    }
}
