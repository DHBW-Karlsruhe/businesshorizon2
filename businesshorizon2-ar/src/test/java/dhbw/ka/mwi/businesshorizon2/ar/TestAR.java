package dhbw.ka.mwi.businesshorizon2.ar;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestAR {

    @Test
    public void testEqualValues() {
        for (int i = 0; i < 1000; i++) {
            final double[] timeSeries = {2000d, 2000d, 2000d, 2000d};
            final double[] predictions = AR.predict(timeSeries, 2, 2);
            assertArrayEquals(new double[]{2000d,2000d}, predictions,0.01);
        }
    }
}
