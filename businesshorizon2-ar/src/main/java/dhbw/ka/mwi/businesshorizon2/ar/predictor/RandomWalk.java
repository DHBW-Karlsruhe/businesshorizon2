package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalk {

    private final double stdDev;

    public RandomWalk(final double stdDev) {
        this.stdDev = stdDev;
    }

    double getRandomWalk() {
        return stdDev * (ThreadLocalRandom.current().nextBoolean() ? 1 : -1);
    }

}
