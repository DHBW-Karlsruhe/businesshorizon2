package dhbw.ka.mwi.businesshorizon2.ar.model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestApacheCoefficient {

	@Test
	public void testCoeffs3() {
		final double[] data = { 77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d, 79644d };

		final ARModel model = new YuleWalkerModelCalculator().getModel(data, 3);
		assertArrayEquals(new double[] { -0.21866, -0.79283, -0.00786 }, model.getCoefficients(), 0.00001);
	}

	@Test
	public void testCoeffs2() {
		final double[] data = { 77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d, 79644d };

		final ARModel model = new YuleWalkerModelCalculator().getModel(data, 2);
		assertArrayEquals(new double[] { -0.21244, -0.79116 }, model.getCoefficients(), 0.00001);
	}

}
