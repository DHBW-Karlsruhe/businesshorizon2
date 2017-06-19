package dhbw.ka.mwi.businesshorizon2.cfsimple;

abstract class SteppingCFAlgorithm implements CFAlgorithm {

    abstract CFIntermediateResult step(final CFParameter parameter, final CFIntermediateResult intermediate);

    private static double[] getInit(final int numPerioden){
        final double[] init = new double[numPerioden];
        for (int i = 0; i < init.length; i++) {
            init[i] = 1;
        }
        return init;
    }

    @Override
    public double calculateUWert(final CFParameter parameter) {
        CFIntermediateResult intermediate = new CFIntermediateResult(getInit(parameter.numPerioden()),getInit(parameter.numPerioden()),getInit(parameter.numPerioden()));
        while (true){
            final CFIntermediateResult next = step(parameter,intermediate);
            if(Math.abs(next.getuWert()[0] - intermediate.getuWert()[0]) < CFConfig.getPrecision()){
                return next.getuWert()[0];
            }
            intermediate = next;
        }
    }
}
