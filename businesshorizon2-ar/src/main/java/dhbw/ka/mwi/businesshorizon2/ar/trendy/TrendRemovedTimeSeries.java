package dhbw.ka.mwi.businesshorizon2.ar.trendy;

public class TrendRemovedTimeSeries {

    private final double[] timeSeriesWithoutTrend;
    private final double slope;
    private final double intercept;

    TrendRemovedTimeSeries(final double[] timeSeriesWithoutTrend, final double slope, final double intercept) {
        this.timeSeriesWithoutTrend = timeSeriesWithoutTrend;
        this.slope = slope;
        this.intercept = intercept;
    }

    public double[] getTimeSeriesWithoutTrend() {
        return timeSeriesWithoutTrend;
    }

    public double[] getTimeSeriesWithTrend(final double[] timeSeriesWithoutTrend){
        final double[] timeSeriesWithTrend = new double[timeSeriesWithoutTrend.length];

        for (int i = 0; i < timeSeriesWithoutTrend.length; i++) {
            double trend = (this.timeSeriesWithoutTrend.length + i) * slope + intercept;
            timeSeriesWithTrend[i] = timeSeriesWithoutTrend[i] + trend;
        }
        return timeSeriesWithTrend;
    }

}
