package dhbw.ka.mwi.businesshorizon2.ar.trendy;

public class TrendRemovedTimeSeries {

    private final double[] timeSeriesWithoutTrend;
    private final double[] trend;

    TrendRemovedTimeSeries(final double[] timeSeriesWithoutTrend, final double[] trend) {
        this.timeSeriesWithoutTrend = timeSeriesWithoutTrend;
        this.trend = trend;
    }

    public double[] getTimeSeriesWithoutTrend() {
        return timeSeriesWithoutTrend;
    }

    public double[] getTrend() {
        return trend;
    }

    public double[] getTimeSeriesWithTrend(final double[] timeSeriesWithoutTrend){
        if(timeSeriesWithoutTrend.length != trend.length){
            throw new IllegalArgumentException("The length of the time series is " + timeSeriesWithoutTrend.length + " ,but the trend has a length of " + trend.length);
        }
        final double[] timeSeriesWithTrend = new double[trend.length];

        for (int i = 0; i < timeSeriesWithoutTrend.length; i++) {
            timeSeriesWithTrend[i] = timeSeriesWithoutTrend[i] + trend[i];
        }
        return timeSeriesWithTrend;
    }

}
