package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestTrendRemover {

    @Test
    public void testTrendRemover() {
        final double[] timeSeries = {10,15,10,22,19,30};
        final TrendRemovedTimeSeries trendRemovedTimeSeries = TrendRemover.removeTrend(timeSeries);
        assertArrayEquals(new double[]{1.190476,2.647619,-5.89524,2.561905,-3.98095,3.47619}, trendRemovedTimeSeries.getTimeSeriesWithoutTrend(),0.01);
    }
}
