package dhbw.ka.mwi.businesshorizon2.demo.calc;

class TimeSeries {

    private final double[] values;

    TimeSeries(final double[] values) {
        this.values = values;
    }

    public double[] getValues() {
        return values;
    }

    public double[] applyModifications(final double[] timeSeries) {
        return timeSeries;
    }
}
