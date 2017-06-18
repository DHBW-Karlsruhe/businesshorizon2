package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class APV implements CFAlgorithm<CFEntityParameter> {

    private static double getUWFiktiv(final CFEntityParameter parameter, final int periode) {
        double summe = 0;
        for (int i = periode + 1; i < parameter.numPerioden() - 1; i++) {
            summe += parameter.getFCF()[i] / Math.pow(1 + parameter.getEKKosten(), i - periode);
        }
        return summe + parameter.getFCF()[parameter.numPerioden() - 1] /  (parameter.getEKKosten() * Math.pow(1 + parameter.getEKKosten(), parameter.numPerioden() - 2 - periode));
    }

    private static double getGesamtkapital(final CFEntityParameter parameter, final int periode) {
        return getUWFiktiv(parameter,periode) + TaxShieldCalculator.calculateTaxShield(parameter,periode);
    }

    private static double calculateUWert(final CFEntityParameter parameter, final int periode) {
        return getGesamtkapital(parameter,periode) - parameter.getFK()[periode];
    }

    @Override
    public double calculateUWert(final CFEntityParameter parameter) {
        return calculateUWert(parameter,0);
    }
}
