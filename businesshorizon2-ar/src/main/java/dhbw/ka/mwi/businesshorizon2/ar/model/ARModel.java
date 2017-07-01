package dhbw.ka.mwi.businesshorizon2.ar.model;

import org.apache.commons.math3.stat.descriptive.moment.Mean;

import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictor;
import dhbw.ka.mwi.businesshorizon2.ar.predictor.ARPredictorRunner;

public class ARModel {

    /**
     * Koeffizienten der AR-Model Gleichung
     */
    private final double[] coefficients;
    private final double[] timeSeries;

    public ARModel(final double[] coefficients, final double[] timeSeries) {
        this.coefficients = coefficients;
        this.timeSeries = timeSeries;
    }

    /**
     * Prognostizert die zukünftigen Werte der Zeitreihe
     * @param numPeriods Die Anzahl an zukünftigen Zeitpunkten, die prognostiziert werden
     * @return Die prognostizierten Werte der Zeitreihe
     */
    public double[] predict(final int numPeriods) {
        return new ARPredictorRunner(new ARPredictor()).runPredictions(timeSeries, coefficients, numPeriods);
    }
    
    /* Berechnet die Güte des AR-Modells
     * Also wie gut das AR-Modell auf die Zeitreihe passt
     */
    public double calculateQuality() {
    	double result = 0;
    	final double avg = new Mean().evaluate(timeSeries);
    	for(int i = coefficients.length+1; i < timeSeries.length;i++){
    		double prognose = 0;
    		for (int j = 0; j < coefficients.length; j++) {
    			prognose += coefficients[j] * (timeSeries[j]-avg);
    		}
    		double error = timeSeries[i]-avg-prognose;
    		
    		result += error*error;
    	}
    	return Math.sqrt(result/(timeSeries.length-coefficients.length))/avg;
    }

    public double[] getCoefficients() {
        return coefficients;
    }
}

