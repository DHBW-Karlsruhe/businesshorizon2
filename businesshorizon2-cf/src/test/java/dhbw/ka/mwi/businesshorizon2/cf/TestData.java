package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;

public class TestData {

	private final CFParameter parameters;
	private final double uWert;
	private final double wacc;
	private final double taxShield;
	private final double ekKostVersch;
	private final double entityUeberschuss;
	private final double equityUeberschuss;

	public TestData(final CFParameter parameters, final double uWert, final double wacc, final double taxShield,
			final double ekKostVersch, final double entityUeberschuss, final double equityUeberschuss) {
        this.parameters = parameters;
		this.uWert = uWert;
		this.wacc = wacc;
		this.taxShield = taxShield;
		this.ekKostVersch = ekKostVersch;
		this.entityUeberschuss = entityUeberschuss;
		this.equityUeberschuss = equityUeberschuss;
	}

	public CFParameter getParameters() {
		return parameters;
	}

	public double getuWert() {
		return uWert;
	}

	public double getWacc() {
		return wacc;
	}

	public double getTaxShield() {
		return taxShield;
	}

	public double getEkKostVersch() {
		return ekKostVersch;
	}

	public double getEntityUeberschuss() {
		return entityUeberschuss;
	}

	public double getEquityUeberschuss() {
		return equityUeberschuss;
	}

}
