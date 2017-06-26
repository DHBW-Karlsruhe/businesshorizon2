package dhbw.ka.mwi.businesshorizon2.cf;

public class FTE implements CFAlgorithm<CFResult> {

    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFTE()[periode] / intermediate.getEkKost()[periode];
        }
        return (intermediate.getuWert()[periode + 1] + parameter.getFTE()[periode + 1]) / (1 + intermediate.getEkKost()[periode + 1]);
    }

    @Override
    public CFResult calculateUWert(final CFParameter parameter) {
        final CFIntermediateResult result = Stepper.performStepping(Stepper.getStartResult(parameter.numPerioden()), cfIntermediateResult -> {
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
        return new CFResult(result.getuWert()[0]);
    }
}
