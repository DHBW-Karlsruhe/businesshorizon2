package dhbw.ka.mwi.businesshorizon2.ar.trendy;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Enfernt den Trend von einer Zeitreihe und speichert diese beiden in ein Objekt
 */
public final class TrendRemover {

    private TrendRemover() {
    }

    /**
     * Enfernt den Trend von einer Zeitreihe und speichert diese beiden in ein Objekt
     *
     * @param timeSeriesWithTrend Zeitreihe von dem der Trend entfernt wird
     * @return Ein Objekt, welche die trendbereinigte Zeitreihe und den Trend enthält
     */
    public static TrendRemovedTimeSeries removeTrend(final double[] timeSeriesWithTrend){
        final double[] timeSeriesWithoutTrend = new double[timeSeriesWithTrend.length];

        //Ermittle den Trend der Zeitreihe
        final SimpleRegression regression = getRegression(timeSeriesWithTrend);
        final double slope = regression.getSlope();
        final double intercept = regression.getIntercept();

        //Entferne den Trend
        for (int i = 0; i < timeSeriesWithTrend.length; i++) {
            double trend = i * slope + intercept;
            timeSeriesWithoutTrend[i] = timeSeriesWithTrend[i] - trend;
        }
        //Kapsele den Trend und die Zeitreihe in ein Objekt und gebe es aus
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
