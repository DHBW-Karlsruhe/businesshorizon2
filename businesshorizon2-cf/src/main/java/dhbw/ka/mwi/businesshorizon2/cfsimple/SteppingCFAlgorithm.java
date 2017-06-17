package dhbw.ka.mwi.businesshorizon2.cfsimple;

public abstract class SteppingCFAlgorithm<T extends CFParameter> implements CFAlgorithm<T> {

    abstract CFIntermediateResult step(final T parameter, final CFIntermediateResult intermediate);

    private static double[] getInit(final int numPerioden, final double initVal){
        final double[] init = new double[numPerioden];
        for (int i = 0; i < init.length; i++) {
            init[i] = initVal;
        }
        return init;
    }

    @Override
    public double calculateUWert(final T parameter) {
        CFIntermediateResult intermediate = new CFIntermediateResult(getInit(parameter.numPerioden(),1055),getInit(parameter.numPerioden(),2315),getInit(parameter.numPerioden(),0.12));

        for (int i = 0; i < 200; i++) {
            intermediate = step(parameter,intermediate);
        }

        return intermediate.getuWert()[0];
    }
}
