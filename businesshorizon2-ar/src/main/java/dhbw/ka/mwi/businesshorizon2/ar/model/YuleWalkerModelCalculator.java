package dhbw.ka.mwi.businesshorizon2.ar.model;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.util.Arrays;

public class YuleWalkerModelCalculator implements ARModelCalculator {

	@Override
	public ARModel getModel(final double[] timeSeries, final int p) {
		final RealMatrix yuleWalkerMatrix = buildYuleWalkerMatrix(timeSeries, p);
		final RealMatrix inverted = new LUDecomposition(yuleWalkerMatrix).getSolver().getInverse();

		final RealMatrix coeffizientMatrix = new Array2DRowRealMatrix(p, 1);
		for (int i = 0; i < coeffizientMatrix.getRowDimension(); i++) {
			coeffizientMatrix.setEntry(i, 0, getAutocorrelation(timeSeries, i + 1));
		}
		return new ARModel(inverted.multiply(coeffizientMatrix).getColumn(0),timeSeries);
	}

	private static double getAutocorrelation(final double[] timeSeries, final int p) {
		final double covariance = new Covariance().covariance(Arrays.copyOfRange(timeSeries, 0, timeSeries.length - p),
				Arrays.copyOfRange(timeSeries, p, timeSeries.length), false);
		final double variance = new Variance(false).evaluate(timeSeries);
		return covariance / variance;
	}

	private static int getPForMatrix(final int row, final int col) {
		return row > col ? row - col : col - row;
	}

	private static RealMatrix buildYuleWalkerMatrix(final double[] timeSeries, final int p) {
		final RealMatrix matrix = new Array2DRowRealMatrix(p, p);
		for (int row = 0; row < matrix.getRowDimension(); row++) {
			for (int col = 0; col < matrix.getColumnDimension(); col++) {
				matrix.setEntry(row, col, getAutocorrelation(timeSeries, getPForMatrix(row, col)));
			}
		}

		return matrix;
	}

}
