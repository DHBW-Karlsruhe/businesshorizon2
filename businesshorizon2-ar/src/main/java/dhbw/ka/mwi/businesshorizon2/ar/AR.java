package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;

public final class AR {

    private AR() {
	}

	/**
	 * Erstellt das AR Modell für die Zeitreihe
	 * Es wird der ModelCalculator, der in ARConfig angegeben ist, verwendet
	 * Der Grad des AR-Modells (p) wird gleich 2 gesetzt
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @return Das AR Modell für die Zeitreihe
	 */
	public static ARModel getModel(final double[] timeSeries){
		return getModel(timeSeries, 2);
	}
	/**
	 * Erstellt das AR Modell für die Zeitreihe
     * Es wird der ModelCalculator, der in ARConfig angegeben ist, verwendet
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @param p Der Grad des AR-Modells
	 * @return Das AR Modell für die Zeitreihe
	 */
	public static ARModel getModel(final double[] timeSeries, final int p){
		return ARConfig.getModelCalculator().getModel(timeSeries, p);
	}

	/**
	 * Führt direkt eine Vorhersage für die Zeitreihe aus
	 * Dafür wird das Modell anhand der oberen getModel-Methode erstellt
     * Mithilfe der predict-Methode des Modells erhalten wir die Prognose
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @param p Der Grad des AR-Modells
	 * @param numPeriods Wie viele Perioden vorhergesagt werden sollen
	 * @return Die prognostizierte Zeitreihe
	 */
	public static double[] predict(final double[] timeSeries, final int p, final int numPeriods) {
		return getModel(timeSeries,p).predict(numPeriods);
	}

}
