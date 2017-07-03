package dhbw.ka.mwi.businesshorizon2.ar;

import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.ar.AssertRelative.assertRelative;

public class TestAR {

    @Test
    public void testEqualValues() {
        for (int i = 0; i < 1000; i++) {
            final double[] timeSeries = {2000d, 2000d, 2000d, 2000d};
            final double[] predictions = AR.predict(timeSeries, 2, 2);
            assertRelative(new double[]{2000d, 2000d}, predictions);
        }
    }
}
