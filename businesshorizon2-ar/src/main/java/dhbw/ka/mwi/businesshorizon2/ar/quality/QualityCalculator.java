package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;

/**
 * Berechnet die Güte eines AR-Modells.
 */
@FunctionalInterface
public interface QualityCalculator {

    /**
     * Berechnet die Güte eines AR-Modells.
     * Also wie gut das AR-Modell auf die Zeitreihe passt.
     * Dafür wird der durschnittliche quadratische Fehler betrachtet.
     * @param model Das AR-Modell.
     * @return Die Güte.
     */
    double calculateQuality(final ARModel model);
}
