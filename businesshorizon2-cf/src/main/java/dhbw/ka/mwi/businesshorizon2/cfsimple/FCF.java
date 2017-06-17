package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class FCF extends SteppingCFAlgorithm<CFEntityParameter> {

    private static double getWACC(final CFEntityParameter parameter, final CFIntermediateResult intermediate, final int periode){
        final double ekQoute = intermediate.getuWert()[periode - 1] / intermediate.getGk()[periode - 1];
        final double fkQoute = parameter.getFK()[periode - 1] / intermediate.getGk()[periode - 1];
        return intermediate.getEkKost()[periode] * ekQoute + parameter.getFKKosten() * (1 - parameter.getuSteusatz()) * fkQoute;
    }

    private static double getGK(final CFEntityParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return intermediate.getGk()[periode - 1];
            //return parameter.getFCF()[periode] / getWACC(parameter,intermediate,periode); //TODO das hier ist eigentlich korrekt
        }
        return (getGK(parameter,intermediate,periode + 1) + parameter.getFCF()[periode + 1]) / (1 + getWACC(parameter,intermediate,periode + 1));
    }

    private static double calculateUWert(final CFEntityParameter parameter, final CFIntermediateResult intermediate, final int periode){
        return intermediate.getGk()[periode] - parameter.getFK()[periode];
    }
    @Override
    CFIntermediateResult step(final CFEntityParameter parameter, final CFIntermediateResult intermediate){
        final double[] uWert = new double[parameter.numPerioden()];
        final double[] gk = new double[parameter.numPerioden()];
        final double[] ekKost = new double[parameter.numPerioden()];

        for (int i = 0; i < parameter.numPerioden(); i++) {
            uWert[i] = calculateUWert(parameter,intermediate,i);
            gk[i] = getGK(parameter,intermediate,i);
            if(i > 0) {
                ekKost[i] = EKKostVerschCalculator.getEKKostenVersch(parameter, intermediate, i);
            }
        }

        return new CFIntermediateResult(uWert,gk,ekKost);
    }
}
