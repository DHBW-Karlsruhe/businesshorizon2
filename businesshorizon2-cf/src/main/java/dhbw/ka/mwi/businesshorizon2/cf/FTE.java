package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Das Flow-to-Equity-Verfahren zur Berechnung des Unternehmenswertes.
 */
public class FTE implements CFAlgorithm<CFResult> {

	/**
	 * Berechnet den Unternehmenswert mittels des FTE-Verfahrens.
     * Basiert auf Formel 95 vom Ballwieser.
	 *
	 * @param parameter enthält alle Paramter, die für die Berechnung des Unternehmenswer wichtig sind.
	 * @param intermediate enthält die Zwischenergebnisse (Stichwort Iteration).
	 * @param periode gibt die Periode an, für die der Unternehmenswert berechnet werden soll.
	 * @return Gibt den Unternehmenswert als double Wert zurück.
	 */
    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFTE()[periode] / intermediate.getEkKost()[periode];
        }
        return (intermediate.getuWert()[periode + 1] + parameter.getFTE()[periode + 1]) / (1 + intermediate.getEkKost()[periode + 1]);
    }

    /**
     * Die öffentliche Methode zur Berechnung des Unternehmenswertes.
     * @return Gibt alle Daten, die bei Berechnung des Unternehmenswertes relevant sind, inklusive des Unternehmenswertes als @{@link CFResult} zurück.
     */
    @Override
    public CFResult calculateUWert(final CFParameter parameter) {
        // Gibt dem Stepper mit, wie eine Iteration der Unternehmenswertberechnung bei dem FTE-Verfahren implementiert ist
        // Mit dieser führt der Stepper solange Iteration durch bis eine gewünschte Präzension erreicht ist
        final CFIntermediateResult result = Stepper.performStepping(Stepper.getStartResult(parameter.numPerioden()), cfIntermediateResult -> {
            final double[] uWert = new double[parameter.numPerioden()];
            final double[] ekKost = new double[parameter.numPerioden()];

            //Speichert Zwischenergebnisse in CFIntermediate Objekten
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
