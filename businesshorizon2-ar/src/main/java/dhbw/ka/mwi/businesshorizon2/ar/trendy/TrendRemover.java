package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public final class TrendRemover {

    private TrendRemover() {
    }

    public static TrendRemovedTimeSeries removeTrend(final double[] timeSeriesWithTrend){
        final double[] timeSeriesWithoutTrend = new double[timeSeriesWithTrend.length];

        final SimpleRegression regression = getRegression(timeSeriesWithTrend);
        final double slope = regression.getSlope();
        final double intercept = regression.getIntercept();

        for (int i = 0; i < timeSeriesWithTrend.length; i++) {
            double trend = i * slope + intercept;
            timeSeriesWithoutTrend[i] = timeSeriesWithTrend[i] - trend;
        }
        return new TrendRemovedTimeSeries(timeSeriesWithoutTrend,slope,intercept);
    }

    private static SimpleRegression getRegression(final double[] timeSeries){
        final SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < timeSeries.length; i++) {
            regression.addData(i,timeSeries[i]);
        }
        return regression;
    }


}
