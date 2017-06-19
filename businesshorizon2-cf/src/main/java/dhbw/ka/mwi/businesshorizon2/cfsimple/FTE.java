package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class FTE extends SteppingCFAlgorithm {

    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFTE()[periode] / intermediate.getEkKost()[periode];
        }
        return (intermediate.getuWert()[periode + 1] + parameter.getFTE()[periode + 1]) / (1 + intermediate.getEkKost()[periode + 1]);
    }

    @Override
    CFIntermediateResult step(final CFParameter parameter, final CFIntermediateResult intermediate){
        final double[] uWert = new double[parameter.numPerioden()];
        final double[] ekKost = new double[parameter.numPerioden()];
        for (int i = 0; i < parameter.numPerioden(); i++) {
            uWert[i] = calculateUWert(parameter,intermediate,i);
            if(i > 0) {
                ekKost[i] = CFConfig.getEkKostVerschCalculator().calculateEKKostenVersch(parameter, intermediate, i);
            }
        }

        return new CFIntermediateResult(uWert,null,ekKost);
    }
}
