package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;

public final class AR {

	private AR() {
	}

	/**
	 * Erstellt das AR Modell für die Zeitreihe
	 * Es wird der ModelCalculator aus ARConfig verwendet
	 * Der Parameter p wird automatisch bestimmt
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @return Das AR Modell für die Zeitreihe
	 */
	public static ARModel getModel(final double[] timeSeries){
		return getModel(timeSeries, 2);
	}
	/**
	 * Erstellt das AR Modell für die Zeitreihe
	 * Es wird der ModelCalculator aus ARConfig verwendet
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @param p Wie viele Koeffizienten ermittelt werden sollen
	 * @return Das AR Modell für die Zeitreihe
	 */
	public static ARModel getModel(final double[] timeSeries, final int p){
		return ARConfig.getModelCalculator().getModel(timeSeries, p);
	}

	/**
	 * Führt direkt eine Vorhersage für die Zeitreihe aus
	 * Es wird der ModelCalculator aus ARConfig verwendet
	 * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
	 * @param p Wie viele Koeffizienten ermittelt werden sollen
	 * @param numPeriods Wie viele Perioden vorhergesagt werden sollen
	 * @return Die Vorhersage
	 */
	public static double[] predict(final double[] timeSeries, final int p, final int numPeriods) {
		return getModel(timeSeries,p).predict(numPeriods);
	}

}
