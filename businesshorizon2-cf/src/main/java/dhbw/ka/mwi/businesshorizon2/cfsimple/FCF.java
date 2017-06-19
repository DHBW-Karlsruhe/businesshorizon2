package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class FCF implements CFAlgorithm<FCFResult> {

    private static double getWACC(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        final double ekQoute = intermediate.getuWert()[periode - 1] / intermediate.getGk()[periode - 1];
        final double fkQoute = parameter.getFK()[periode - 1] / intermediate.getGk()[periode - 1];
        return intermediate.getEkKost()[periode] * ekQoute + parameter.getFKKosten() * (1 - parameter.getuSteusatz()) * fkQoute;
    }

    private static double getGK(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return intermediate.getGk()[periode - 1];
            //return parameter.getFCF()[periode] / getWACC(parameter,intermediate,periode); //TODO das hier ist eigentlich korrekt
        }
        return (getGK(parameter,intermediate,periode + 1) + parameter.getFCF()[periode + 1]) / (1 + getWACC(parameter,intermediate,periode + 1));
    }

    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        return intermediate.getGk()[periode] - parameter.getFK()[periode];
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
        final CFIntermediateResult start = new CFIntermediateResult(getInit(parameter.numPerioden()),getInit(parameter.numPerioden()),getInit(parameter.numPerioden()));
        final CFIntermediateResult result = Stepper.performStepping(start, cfIntermediateResult -> {
            final double[] uWert = new double[parameter.numPerioden()];
            final double[] gk = new double[parameter.numPerioden()];
            final double[] ekKost = new double[parameter.numPerioden()];

            for (int i = 0; i < parameter.numPerioden(); i++) {
                uWert[i] = calculateUWert(parameter,cfIntermediateResult,i);
                gk[i] = getGK(parameter,cfIntermediateResult,i);
                if(i > 0) {
                    ekKost[i] = CFConfig.getEkKostVerschCalculator().calculateEKKostenVersch(parameter, cfIntermediateResult, i);
                }
            }

            return new CFIntermediateResult(uWert,gk,ekKost);
        });
        return new FCFResult(result.getuWert()[0],result.getGk()[0]);
    }
}
