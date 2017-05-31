package dhbw.ka.mwi.businesshorizon2.ar;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestAR {

    @Test
    public void testEqualValues() {
        for (int i = 0; i < 1000; i++) {
            final double[] timeSeries = {2000d, 2000d, 2000d, 2000d};
            final ARModel model = AR.getModel(timeSeries, 2);
            final double[] predictions = model.predict(2);
            assertArrayEquals(new double[]{2000d,2000d}, predictions,0.01);
        }
    }
}
