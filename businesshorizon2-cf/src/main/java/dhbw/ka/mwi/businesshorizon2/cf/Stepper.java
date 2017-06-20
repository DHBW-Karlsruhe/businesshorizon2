package dhbw.ka.mwi.businesshorizon2.cf;

import java.util.function.Function;

final class Stepper {

    private static final int MAX_LOOPS = 500;

    private Stepper() {
    }

    static CFIntermediateResult performStepping(final CFIntermediateResult startValues, final Function<CFIntermediateResult, CFIntermediateResult> stepFunction){
        CFIntermediateResult intermediate = startValues;
        int loops = 0;
        while (loops <= MAX_LOOPS){
            final CFIntermediateResult next = stepFunction.apply(intermediate);
            if(Math.abs(next.getuWert()[0] - intermediate.getuWert()[0]) < CFConfig.getPrecision()){
                return next;
            }
            intermediate = next;
            loops++;
        }
        return intermediate;
    }
}
