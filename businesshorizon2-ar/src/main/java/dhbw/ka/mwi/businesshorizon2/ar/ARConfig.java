package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.YuleWalkerModelCalculator;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModelCalculator;

public final class ARConfig {

	private static ARModelCalculator modelCalculator = new YuleWalkerModelCalculator();

	private ARConfig() {
	}

	public static ARModelCalculator getModelCalculator() {
		return modelCalculator;
	}

	public static void setModelCalculator(final ARModelCalculator coefficient) {
		ARConfig.modelCalculator = coefficient;
	}

}
