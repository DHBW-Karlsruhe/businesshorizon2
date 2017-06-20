package dhbw.ka.mwi.businesshorizon2.cf;

public class TaxShieldEKKostVerschCalculator2 implements EKKostVerschCalculator {

    @Override
    public double calculateEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode) {
        return parameter.getEKKosten() + (parameter.getEKKosten() - parameter.getFKKosten() * (1 - parameter.getuSteusatz())) * parameter.getFK()[periode - 1] / intermediate.getuWert()[periode - 1] + (TaxShieldCalculator.calculateTaxShield(parameter,periode) - TaxShieldCalculator.calculateTaxShield(parameter,periode - 1) * (1 + parameter.getEKKosten())) / intermediate.getuWert()[periode - 1];
    }

}
