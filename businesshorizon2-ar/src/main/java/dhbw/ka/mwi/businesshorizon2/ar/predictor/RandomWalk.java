package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Random;

public class RandomWalk {

    private final Random random;
    private final double stdDev;

    public RandomWalk(final double stdDev) {
        this(new Random(), stdDev);
    }

    RandomWalk(final Random random, final double stdDev) {
        this.random = random;
        this.stdDev = stdDev;
    }

    double getRandomWalk() {
        return stdDev * (random.nextBoolean() ? 1 : -1);
    }

}
