package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.ar.AssertRelative.assertRelative;

public class TestTrendRemovedTimeSeries {
    @Test
    public void testTrendRemover() {
        final double[] timeSeries = {10, 15, 10, 22, 19, 30};
        final TrendRemovedTimeSeries trendRemovedTimeSeries = TrendRemover.removeTrend(timeSeries);
        assertRelative(new double[]{32.06667, 37.60952}, trendRemovedTimeSeries.getTimeSeriesWithTrend(new double[]{10.809, 12.809}));

    }
}
