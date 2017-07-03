package dhbw.ka.mwi.businesshorizon2.demo.calc;

import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemovedTimeSeries;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemover;

class TrendyTimeSeries extends TimeSeries {

    private final TrendRemovedTimeSeries trendRemovedTimeSeries;

    TrendyTimeSeries(final double[] values) {
        super(values);
        trendRemovedTimeSeries = TrendRemover.removeTrend(values);
    }

    @Override
    public double[] getValues() {
        return trendRemovedTimeSeries.getTimeSeriesWithoutTrend();
    }

    @Override
    public double[] applyModifications(final double[] timeSeries) {
        return trendRemovedTimeSeries.getTimeSeriesWithTrend(timeSeries);
    }
}
