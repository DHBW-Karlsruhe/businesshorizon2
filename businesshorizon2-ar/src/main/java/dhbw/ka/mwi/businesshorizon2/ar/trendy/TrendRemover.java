package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public final class TrendRemover {

    private TrendRemover() {
    }

    public static TrendRemovedTimeSeries removeTrend(final double[] timeSeriesWithTrend){
        final double[] trend = new double[timeSeriesWithTrend.length];
        final double[] timeSeriesWithoutTrend = new double[timeSeriesWithTrend.length];

        final SimpleRegression regression = getRegression(timeSeriesWithTrend);

        for (int i = 0; i < trend.length; i++) {
            trend[i] = (i + 1) * regression.getSlope() + regression.getIntercept();
            timeSeriesWithoutTrend[i] = timeSeriesWithTrend[i] - trend[i];
        }
        return new TrendRemovedTimeSeries(timeSeriesWithoutTrend,trend);
    }

    private static SimpleRegression getRegression(final double[] timeSeries){
        final SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < timeSeries.length; i++) {
            regression.addData(i + 1,timeSeries[i]);
        }
        return regression;
    }


}
