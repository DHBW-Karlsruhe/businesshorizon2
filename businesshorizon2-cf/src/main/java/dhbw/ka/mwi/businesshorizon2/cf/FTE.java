package dhbw.ka.mwi.businesshorizon2.cf;

public class FTE implements CFAlgorithm<CFResult> {

	/**
	 * calculateUWert berechnet den Unternehmenswert mittels FTE Verfahren
	 * 
	 * @param parameter enthält alle Paramter die zur Berechnung des Unternehmenswer wichtig sind
	 * @param intermediate enthält die Zwischenergebnisse (Stichwort Iteration)
	 * @param periode gibt die Periode an für die der Unternehmenswert berechnet werden soll
	 * @return gibt den Unternehmenswert als double Wert zurück
	 */
    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFTE()[periode] / intermediate.getEkKost()[periode];
        }
        return (intermediate.getuWert()[periode + 1] + parameter.getFTE()[periode + 1]) / (1 + intermediate.getEkKost()[periode + 1]);
    }

    @Override
    /**
     * calculateUWert ist die öffentliche Methode zur Berechnung des Unternehmenswert
     * @return gibt alle Parameter die zur Berechnung des Unternehmenswert inklusive den Unternehmenswert als CFResult zurück
     */
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
