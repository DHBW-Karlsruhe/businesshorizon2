package dhbw.ka.mwi.businesshorizon2.ar.model;

import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.ar.AssertRelative.assertRelative;

public class TestYuleWalker {

    @Test
    public void testCoeffs3() {
        final double[] data = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d, 79644d};

        final ARModel model = new YuleWalkerModelCalculator().getModel(data, 3);
        assertRelative(new double[]{-0.21866, -0.79283, -0.00786}, model.getCoefficients());
    }

    @Test
    public void testCoeffs2() {
        final double[] data = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d, 79644d};

        final ARModel model = new YuleWalkerModelCalculator().getModel(data, 2);
        assertRelative(new double[]{-0.21244, -0.79116}, model.getCoefficients());
    }

}
