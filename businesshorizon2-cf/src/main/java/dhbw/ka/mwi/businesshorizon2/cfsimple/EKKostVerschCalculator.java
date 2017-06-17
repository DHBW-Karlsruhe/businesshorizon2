package dhbw.ka.mwi.businesshorizon2.cfsimple;

final class EKKostVerschCalculator {
    private EKKostVerschCalculator() {
    }

    static double getEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        double summe = 0;

        for (int i = periode; i < parameter.numPerioden() - 1; i++) {
            summe+= parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[i - 1] / Math.pow(1 + parameter.getFKKosten(), periode - (i - 1));
        }
        final double sfkt = parameter.getuSteusatz() * parameter.getFK()[parameter.numPerioden() - 2] / Math.pow(1 + parameter.getFKKosten(),parameter.numPerioden() - periode - 1);
        final double fkt1Summe = parameter.getFK()[periode - 1] - summe - sfkt;
        final double subtraktion = fkt1Summe / intermediate.getuWert()[periode - 1];
        return parameter.getEKKosten() + (parameter.getEKKosten() - parameter.getFKKosten()) * subtraktion;
    }
}
