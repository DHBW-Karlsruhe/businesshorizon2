package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class TaxShieldEKKostVerschCalculator implements EKKostVerschCalculator {

    @Override
    public double calculateEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode) {
        return parameter.getEKKosten() + (parameter.getEKKosten() - parameter.getFKKosten()) * (parameter.getFK()[periode - 1] - TaxShieldCalculator.calculateTaxShield(parameter,periode - 1)) / intermediate.getuWert()[periode - 1];
    }

}
