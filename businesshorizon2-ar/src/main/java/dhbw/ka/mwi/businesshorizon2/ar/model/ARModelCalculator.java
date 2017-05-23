package dhbw.ka.mwi.businesshorizon2.ar.model;

@FunctionalInterface
public interface ARModelCalculator {
	ARModel getModel(final double[] timeSeries, final int p);
}
