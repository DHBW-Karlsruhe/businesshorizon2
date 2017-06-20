package dhbw.ka.mwi.businesshorizon2.cf;

public class FCF implements CFAlgorithm<FCFResult> {

    private static double getWACC(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        final double gk = intermediate.getuWert()[periode - 1] + parameter.getFK()[periode - 1];
        final double ekQoute = intermediate.getuWert()[periode - 1] / gk;
        final double fkQoute = parameter.getFK()[periode - 1] / gk;
        return intermediate.getEkKost()[periode] * ekQoute + parameter.getFKKosten() * (1 - parameter.getuSteusatz()) * fkQoute;
    }

    private static double getGK(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFCF()[periode] / getWACC(parameter,intermediate,periode);
        }
        return (getGK(parameter,intermediate,periode + 1) + parameter.getFCF()[periode + 1]) / (1 + getWACC(parameter,intermediate,periode + 1));
    }

    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        return getGK(parameter,intermediate,periode) - parameter.getFK()[periode];
    }

    private static double[] getInit(final int numPerioden){
        final double[] init = new double[numPerioden];
        for (int i = 0; i < init.length; i++) {
            init[i] = 1;
        }
        return init;
    }

    @Override
    public FCFResult calculateUWert(final CFParameter parameter) {
        final CFIntermediateResult start = new CFIntermediateResult(getInit(parameter.numPerioden()),getInit(parameter.numPerioden()));
        final CFIntermediateResult result = Stepper.performStepping(start, cfIntermediateResult -> {
            final double[] uWert = new double[parameter.numPerioden()];
            final double[] ekKost = new double[parameter.numPerioden()];

            for (int i = 0; i < parameter.numPerioden(); i++) {
                uWert[i] = calculateUWert(parameter,cfIntermediateResult,i);
                if(i > 0) {
                    ekKost[i] = CFConfig.getEkKostVerschCalculator().calculateEKKostenVersch(parameter, cfIntermediateResult, i);
                }
            }

            return new CFIntermediateResult(uWert,ekKost);
        });
        return new FCFResult(result.getuWert()[0],result.getuWert()[0] + parameter.getFK()[0]);
    }
}
