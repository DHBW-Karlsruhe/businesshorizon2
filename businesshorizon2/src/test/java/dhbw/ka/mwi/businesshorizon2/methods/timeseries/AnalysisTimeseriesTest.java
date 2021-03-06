package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import org.junit.Assert;
import org.junit.Test;

import Jama.Matrix;

public class AnalysisTimeseriesTest {

	@Test
	public void testCreateMatrix() throws Exception {
		double[] timeseries = { 1, 2, 3, 4 };

		AnalysisTimeseries at = new AnalysisTimeseries();

		Matrix returnValue = at.createMatrix(timeseries);

		double[][] matrix = returnValue.getArray();

		Assert.assertEquals(1.0, matrix[0][0], 0.00001);
		Assert.assertEquals(1.0, matrix[1][1], 0.00001);
		Assert.assertEquals(1.0, matrix[2][2], 0.00001);
		Assert.assertEquals(1.0, matrix[3][3], 0.00001);

	}

	@Test
	public void testCalculateModelParameters() throws Exception {
		AnalysisTimeseries timeseries = new AnalysisTimeseries();

		double[][] array = { { 1, 2, 3 }, { 0, 1, 5 }, { 5, 6, 0 } };

		Matrix matrix = new Matrix(array);
		
		double[] autocorrelations = { 1, 2, 3 };
		double[] parameters = timeseries.calculateModelParameters(matrix, autocorrelations);

		Assert.assertEquals(5.4, parameters[0], 0.001);
		Assert.assertEquals(-4.0, parameters[1], 0.001);
		Assert.assertEquals(1.2, parameters[2], 0.001);

	}

}
