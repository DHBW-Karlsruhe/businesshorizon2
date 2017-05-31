package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class APV implements CFAlgorithm {

    private static double getUWFiktiv(final CFParameter parameter, final int periode) {
        return periode < parameter.numPerioden() - 2 ? getUWFiktiv(parameter, periode + 1) + parameter.getFCF()[periode + 1] / Math.pow(1 + parameter.getEKKosten(), periode + 1) : parameter.getFCF()[periode + 1] / (parameter.getEKKosten() * Math.pow(1 + parameter.getEKKosten(), periode));
    }

    private static double getTaxShield(final CFParameter parameter, final int periode) {
        return periode < parameter.numPerioden() - 2 ? getTaxShield(parameter, periode + 1) + parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[periode] / Math.pow(parameter.getFKKosten() + 1, periode + 1) : parameter.getuSteusatz() * parameter.getFK()[periode] / Math.pow(parameter.getFKKosten() + 1, periode);
    }

    private static double getGesamtkapital(final CFParameter parameter, final int periode) {
        return getUWFiktiv(parameter,periode) + getTaxShield(parameter,periode);
    }

    private static double calculateUWert(final CFParameter parameter, final int periode) {
        return getGesamtkapital(parameter,periode) - parameter.getFK()[periode];
    }

    @Override
    public double calculateUWert(final CFParameter parameter) {
        return calculateUWert(parameter,0);
    }
}
