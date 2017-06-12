package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class FCF implements CFAlgorithm {

    private static double getEKKostenVersch(final CFParameter parameter, final FCFIntermediateResult intermediate, final int periode){
        double summe = 0;

        for (int i = 0; i < parameter.numPerioden() - periode - 1; i++) {
            summe+= parameter.getuSteusatz() * parameter.getFKKosten() * parameter.getFK()[i] / Math.pow(1 + parameter.getFKKosten(), i + 1);
        }

        final double sfkt = parameter.getuSteusatz() * parameter.getFK()[parameter.numPerioden() -1] / Math.pow(1 + parameter.getFKKosten(),parameter.numPerioden() - periode - 1);
        final double fkt1Summe = parameter.getFK()[periode - 1] - summe - sfkt;
        final double subtraktion = fkt1Summe / intermediate.getuWert()[periode - 1];
        return parameter.getEKKosten() + (parameter.getEKKosten() - parameter.getFKKosten()) * subtraktion;
    }

    private static double getWACC(final CFParameter parameter, final FCFIntermediateResult intermediate, final int periode){
        final double ekQoute = intermediate.getuWert()[periode - 1] / intermediate.getGk()[periode - 1];
        final double fkQoute = parameter.getFK()[periode - 1] / intermediate.getGk()[periode - 1];

        return intermediate.getEkKost()[periode] * ekQoute + parameter.getFKKosten() * (1 - parameter.getuSteusatz()) * fkQoute;
    }

    private static double getGK(final CFParameter parameter, final FCFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return intermediate.getGk()[periode - 1];
        }
        return (getGK(parameter,intermediate, periode +1) + parameter.getFCF()[periode + 1]) / (1 + intermediate.getWacc()[periode + 1]);
    }

    private static double calculateUWert(final CFParameter parameter, final FCFIntermediateResult intermediate, final int periode){
        return intermediate.getGk()[periode] - parameter.getFK()[periode];
    }

    static FCFIntermediateResult step(final CFParameter parameter, final FCFIntermediateResult intermediate){
        final double[] uWert = new double[parameter.numPerioden()];
        final double[] gk = new double[parameter.numPerioden()];
        final double[] wacc = new double[parameter.numPerioden()];
        final double[] ekKost = new double[parameter.numPerioden()];
        for (int i = 0; i < parameter.numPerioden(); i++) {
            uWert[i] = calculateUWert(parameter,intermediate,i);
            gk[i] = getGK(parameter,intermediate,i);
            if(i > 0) {
                wacc[i] = getWACC(parameter, intermediate, i);
                ekKost[i] = getEKKostenVersch(parameter, intermediate, i);
            }
        }

        return new FCFIntermediateResult(uWert,gk,wacc,ekKost);
    }
    private static double[] getInit(final int numPerioden){
        final double[] init = new double[numPerioden];
        for (int i = 0; i < init.length; i++) {
            init[i] = 1;
        }
        return init;
    }

    @Override
    public double calculateUWert(final CFParameter parameter) {
        FCFIntermediateResult intermediate = new FCFIntermediateResult(getInit(parameter.numPerioden()),getInit(parameter.numPerioden()),getInit(parameter.numPerioden()),getInit(parameter.numPerioden()));

        for (int i = 0; i < 200; i++) {
            intermediate = step(parameter,intermediate);
        }

        return intermediate.getuWert()[0];
    }
}
