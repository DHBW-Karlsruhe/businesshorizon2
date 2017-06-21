package dhbw.ka.mwi.businesshorizon2.ar.trendy;

/**
 * Eine Klasse, welche einen Trend und eine trendbereinigte Zeitreihe speichert
 */
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

    /**
     * Setzt den Trend für eine andere Zeitreihe fort
     * @param timeSeriesWithoutTrend Die Zeitreihe auf dem der Trend hinzugefügt wird
     * @return Die Zeitreihe addiert mit dem Trend
     */
    public double[] getTimeSeriesWithTrend(final double[] timeSeriesWithoutTrend){
        final double[] timeSeriesWithTrend = new double[timeSeriesWithoutTrend.length];

        for (int i = 0; i < timeSeriesWithoutTrend.length; i++) {
            double trend = (this.timeSeriesWithoutTrend.length + i) * slope + intercept;
            timeSeriesWithTrend[i] = timeSeriesWithoutTrend[i] + trend;
        }
        return timeSeriesWithTrend;
    }

}
