package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;

@FunctionalInterface
public interface QualityCalculator {

    /* Berechnet die Güte des AR-Modells
     * Also wie gut das AR-Modell auf die Zeitreihe passt
     */
    double calculateQuality(final ARModel model);
}
