package dhbw.ka.mwi.businesshorizon2.ar.predictor;

/**
 * Erweitert die @{@link ARPredictor}-Klasse insofern, dass bei der Prognose ein zufälliger Fehler hinzugefügt wird.
 * Zur Berechnung einer Zufallszahl wird die @{@link RandomWalk}-Klasse verwendet.
 */
public class RandomWalkPredictor extends ARPredictor {

    private final RandomWalk randomWalk;

    public RandomWalkPredictor(final RandomWalk randomWalk) {
        this.randomWalk = randomWalk;
    }

    /**
     * Berechnet einen zukünftigen Wert der Zeitreihe.
     * Bei der Vorhersage wird auch ein Fehler (Et) betrachtet.
     * @param timeSeries   Die Zeitreihe.
     * @param coefficients Die Koeffizeinten der AR-Modellgleichung.
     * @param avg          Der Mittelwert der Zeitreihe.
     * @return Die Prognose des nächsten Wertes der Zeitreihe.
     */
    @Override
    public double predict(final double[] timeSeries, final double[] coefficients, final double avg) {
        return super.predict(timeSeries, coefficients, avg) + randomWalk.calculateNextRandomNumber();
    }
}
