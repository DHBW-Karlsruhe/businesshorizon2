package dhbw.ka.mwi.businesshorizon2.cf;

import java.util.function.Function;

final class Stepper {

    private static final int MAX_LOOPS = 500;

    /**
     * Privater Konstruktor für den Stepper
     */
    private Stepper() {
    }

    /**
     * Führt die Iteration eines DCF-Verfahrens durch
     * Und zwar bis eine gewünschte Präzension bei der Ergebnissen und eine maximala Anzahl an Durchläufen erreicht ist
     * @param startValues Anfangswerte der Iterationen
     * @param stepFunction Die Funktion, die iteriert werden soll
     * @return gibt das Ergebnis der letzten Iteration zurück
     */
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

    /**
     * getInitArray initialisiert das Array, wo die Zwischenergebnisse der Unternehmenswertberechnung später gespeichert werden
     * @param numPerioden entspricht der Anzahl an Perioden
     * @return gibt ein double Array zurück wo alle Felder mit einer 1 gefüllt sind
     */
    private static double[] getInitArray(final int numPerioden){
        final double[] init = new double[numPerioden];
        for (int i = 0; i < init.length; i++) {
            init[i] = 1;
        }
        return init;
    }

    /**
     * Initaliesiert den Startwert für den Stepper
     * @param numPerioden Die Anzahl der Perioden
     * @return der Startwert für den Stepper
     */
    static CFIntermediateResult getStartResult(final int numPerioden){
        return new CFIntermediateResult(getInitArray(numPerioden), getInitArray(numPerioden));
    }
}
