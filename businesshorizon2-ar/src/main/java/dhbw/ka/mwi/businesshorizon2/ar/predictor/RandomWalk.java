package dhbw.ka.mwi.businesshorizon2.ar.predictor;

import java.util.Random;

/**
 * Speichert die Informationen, die zu einem RandomWalk benötigt werden
 * und kann dadurch über die calculateNextRandomNumber-Methode die nächste Zufallzahl berechnen.
 */
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

    double calculateNextRandomNumber() {
        return stdDev * (random.nextBoolean() ? 1 : -1);
    }

}
