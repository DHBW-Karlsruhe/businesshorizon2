package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.YuleWalkerModelCalculator;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModelCalculator;

public final class ARConfig {

    /**
     * Die Implementierung des ModelCalculators wird hier konfiguriert
     */
    private static ARModelCalculator modelCalculator = new YuleWalkerModelCalculator();

	private ARConfig() {
	}

    /**
     * Gibt einem den ModelCalculator mit dem das AR-Modell berechnet werden kann
     * @return Implementierung des ModelCalculators
     */
	public static ARModelCalculator getModelCalculator() {
		return modelCalculator;
	}

    /**
     * Setzt die neue Implementierung des ModelCalculators fest
     * @param modelCalculator Eine Implementierung des ModelCalculators
     */
	public static void setModelCalculator(final ARModelCalculator modelCalculator) {
		ARConfig.modelCalculator = modelCalculator;
	}

}
