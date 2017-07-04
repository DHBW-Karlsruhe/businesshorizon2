package dhbw.ka.mwi.businesshorizon2.ar.model;

@FunctionalInterface
public interface ARModelCalculator {

    /**
     * Berechnet das AR-Modell.
     *
     * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll.
     * @param p          Der Grad des AR-Modells.
     * @return Das AR-Modell für die Zeitreihe.
     */
    ARModel getModel(final double[] timeSeries, final int p);
}
