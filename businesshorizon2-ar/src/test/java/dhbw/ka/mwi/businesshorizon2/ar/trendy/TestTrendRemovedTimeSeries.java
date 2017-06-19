package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestTrendRemovedTimeSeries {
    @Test
    public void testTrendRemover() {
        final double[] timeSeries = {10,15,10,22,19,30};
        final TrendRemovedTimeSeries trendRemovedTimeSeries = TrendRemover.removeTrend(timeSeries);
        assertArrayEquals(new double[]{32.06667,37.60952}, trendRemovedTimeSeries.getTimeSeriesWithTrend(new double[]{2,4}),0.01);


    }
}
