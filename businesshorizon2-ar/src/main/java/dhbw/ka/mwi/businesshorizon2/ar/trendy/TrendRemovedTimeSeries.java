package dhbw.ka.mwi.businesshorizon2.ar.trendy;

/**
 * Eine Klasse, welche einen Trend und eine trendbereinigte Zeitreihe speichert
 */
public class TrendRemovedTimeSeries {

    private final double[] timeSeriesWithoutTrend;
    private final double slope;

    TrendRemovedTimeSeries(final double[] timeSeriesWithoutTrend, final double slope) {
        this.timeSeriesWithoutTrend = timeSeriesWithoutTrend;
        this.slope = slope;
    }

    public double[] getTimeSeriesWithoutTrend() {
        return timeSeriesWithoutTrend;
    }

    /**
     * Setzt den Trend für eine andere Zeitreihe fort
     *
     * @param timeSeriesWithoutTrend Die Zeitreihe auf dem der Trend hinzugefügt wird
     * @return Die Zeitreihe addiert mit dem Trend
     */
    public double[] getTimeSeriesWithTrend(final double[] timeSeriesWithoutTrend) {
        final double[] timeSeriesWithTrend = new double[timeSeriesWithoutTrend.length];

        for (int i = 0; i < timeSeriesWithoutTrend.length; i++) {
            final double trend = (this.timeSeriesWithoutTrend.length + i) * slope;
            timeSeriesWithTrend[i] = timeSeriesWithoutTrend[i] + trend;
        }
        return timeSeriesWithTrend;
    }

}
