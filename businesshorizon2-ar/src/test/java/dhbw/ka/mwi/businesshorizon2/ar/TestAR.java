package dhbw.ka.mwi.businesshorizon2.ar;

import org.junit.Test;

public class TestAR {
    @Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
            final double[] timeSeries = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d,
                    79644d};
            AR.predict(timeSeries, 3, 5);
            //assertArrayEquals(new double[]{75584.95,73541.64}, predictions,0.01);
        }
    }
}
