package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class APV implements CFAlgorithm<CFEntityParameter> {

    private static double getUWFiktiv(final CFEntityParameter parameter, final int periode) {
        if (periode < parameter.numPerioden() - 2) {
            return getUWFiktiv(parameter, periode + 1) + parameter.getFCF()[periode + 1] / Math.pow(1 + parameter.getEKKosten(), periode + 1);
        }
        return parameter.getFCF()[periode + 1] / (parameter.getEKKosten() * Math.pow(1 + parameter.getEKKosten(), periode));
    }

    private static double getTaxShield(final CFEntityParameter parameter, final int periode) {
        if (periode < parameter.numPerioden() - 2) {
            return getTaxShield(parameter, periode + 1) + parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[periode] / Math.pow(parameter.getFKKosten() + 1, periode + 1);
        }
        return parameter.getuSteusatz() * parameter.getFK()[periode] / Math.pow(parameter.getFKKosten() + 1, periode);
    }

    private static double getGesamtkapital(final CFEntityParameter parameter, final int periode) {
        return getUWFiktiv(parameter,periode) + getTaxShield(parameter,periode);
    }

    private static double calculateUWert(final CFEntityParameter parameter, final int periode) {
        return getGesamtkapital(parameter,periode) - parameter.getFK()[periode];
    }

    @Override
    public double calculateUWert(final CFEntityParameter parameter) {
        return calculateUWert(parameter,0);
    }
}
