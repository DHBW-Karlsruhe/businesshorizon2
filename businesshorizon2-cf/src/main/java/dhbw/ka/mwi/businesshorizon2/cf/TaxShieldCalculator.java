package dhbw.ka.mwi.businesshorizon2.cf;

final class TaxShieldCalculator {

    private TaxShieldCalculator() {
    }

    static double calculateTaxShield(final CFParameter parameter, final int periode) {
        double summe = 0;
        for (int i = periode + 1; i < parameter.numPerioden() - 1; i++) {
            summe += parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[i - 1] / Math.pow(parameter.getFKKosten() + 1, i - periode);
        }
        return summe + parameter.getuSteusatz() * parameter.getFK()[parameter.numPerioden() - 2] / Math.pow(parameter.getFKKosten() + 1, parameter.numPerioden() - 2 - Math.min(periode,parameter.numPerioden() - 2));
    }
}
