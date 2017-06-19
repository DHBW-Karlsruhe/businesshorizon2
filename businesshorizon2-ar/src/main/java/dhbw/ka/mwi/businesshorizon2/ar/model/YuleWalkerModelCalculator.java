package dhbw.ka.mwi.businesshorizon2.ar.model;

import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.util.Arrays;

/**
 * Die Yule Walker Implementierung des ModelCalculators
 */
public class YuleWalkerModelCalculator implements ARModelCalculator {

    /**
     * Berechne das AR-Modell indem das Yule-Walker Gleichungssystem aufgebaut und gelöst wird
     * @param timeSeries Die Zeitreihe, für die das Modell erstellt werden soll
     * @param p Der Grad des AR-Modells
     * @return Das AR Modell für die Zeitreihe
     */
	@Override
	public ARModel getModel(final double[] timeSeries, final int p) {
		if(new Variance(false).evaluate(timeSeries) == 0){
			return new ARModel(new double[p],timeSeries);
		}
        // Baue das Yule-Walker Gleichungssystem auf
		final RealMatrix yuleWalkerMatrix = buildYuleWalkerMatrix(timeSeries, p);
		final RealVector coeffizientVector = new ArrayRealVector(p);
		for (int i = 0; i < coeffizientVector.getDimension(); i++) {
			coeffizientVector.setEntry(i, getAutocorrelation(timeSeries, i + 1));
		}
		//Löse das Gleichungsystem
		final RealVector solved = new LUDecomposition(yuleWalkerMatrix).getSolver().solve(coeffizientVector);
		return new ARModel(solved.toArray(),timeSeries);
	}

    /**
     * Berechne die Autokorrelationsfunktion für die Zeitreihe in Abhängigkeit von lamda
     * @param timeSeries Dgie Zeitreihe für die die Autokorrelation berechnet werden soll
     * @param lamda Lamda-Operator
     * @return Autokorrelationsfunktion
     */
	private static double getAutocorrelation(final double[] timeSeries, final int lamda) {
		final double covariance = new Covariance().covariance(Arrays.copyOfRange(timeSeries, 0, timeSeries.length - lamda),
				Arrays.copyOfRange(timeSeries, lamda, timeSeries.length), false);
		final double variance = new Variance(false).evaluate(timeSeries);
		return covariance / variance;
	}

	private static int getLamdaForMatrixEntry(final int row, final int col) {
		return row > col ? row - col : col - row;
	}

	private static RealMatrix buildYuleWalkerMatrix(final double[] timeSeries, final int p) {
		final RealMatrix matrix = new Array2DRowRealMatrix(p, p);
		for (int row = 0; row < matrix.getRowDimension(); row++) {
			for (int col = 0; col < matrix.getColumnDimension(); col++) {
				matrix.setEntry(row, col, getAutocorrelation(timeSeries, getLamdaForMatrixEntry(row, col)));
			}
		}

		return matrix;
	}

}
