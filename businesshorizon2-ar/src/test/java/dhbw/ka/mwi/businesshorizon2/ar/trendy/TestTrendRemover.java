package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestTrendRemover {

    @Test
    public void testTrendRemover() {
        final double[] timeSeries = {10, 15, 10, 22, 19, 30};
        final TrendRemovedTimeSeries trendRemovedTimeSeries = TrendRemover.removeTrend(timeSeries);
        assertArrayEquals(new double[]{10, 11.4571, 2.9143, 11.3714, 4.8286, 12.2857}, trendRemovedTimeSeries.getTimeSeriesWithoutTrend(), 0.01);
    }
}
