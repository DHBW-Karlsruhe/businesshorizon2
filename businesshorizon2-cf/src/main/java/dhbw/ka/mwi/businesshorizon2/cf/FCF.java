package dhbw.ka.mwi.businesshorizon2.cf;

/**
 * Der Free-Cashflow-Algoritmus zur Berechnung des Unternehmenswertes
 */
public class FCF implements CFAlgorithm<FCFResult> {

	/**
	 * calculateWACC berechnet den WACC einer bestimmten Periode
	 *
	 * @param parameter CFParameter enthält das Parameterset, welches zur Berechnung der DCF-Methoden benötigt wird
	 * @param intermediate speichert die Eigenkapitalkosten und den Unternehmenswert in der Iteration als Zwischenergebenisse
	 * @param periode gibt an, von welcher Periode der WACC berechnet werden soll
	 *
	 * @return gibt den WACC der bestimmten Periode zurück 
	 */
    private static double calculateWACC(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        final double gk = intermediate.getuWert()[periode - 1] + parameter.getFK()[periode - 1];
        final double ekQoute = intermediate.getuWert()[periode - 1] / gk;
        final double fkQoute = parameter.getFK()[periode - 1] / gk;
        return intermediate.getEkKost()[periode] * ekQoute + parameter.getFKKosten() * (1 - parameter.getuSteusatz()) * fkQoute;
    }

    /**
     * calculateGK berechnet das Gesamtkapital einer bestimmten Periode
     *
     * @param parameter CFParameter enthält das Parameterset, welches zur Berechnung der DCF-Methoden benötigt wird
     * @param intermediate speichert die Eigenkapitalkosten und den Unternehmenswert in der Iteration als Zwischenergebenisse
     * @param periode gibt an, von welcher Periode das Gesamtkapital berechnet werden soll
     *
     * @return gibt das berechnete Gesamtkapital einer bestimmten Periode zurück
     */
    private static double calculateGK(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        if(periode >= parameter.numPerioden() - 1){
            return parameter.getFCF()[periode] / calculateWACC(parameter,intermediate,periode);
        }
        return (calculateGK(parameter,intermediate,periode + 1) + parameter.getFCF()[periode + 1]) / (1 + calculateWACC(parameter,intermediate,periode + 1));
    }

    /**
     * calculateUWert berechnet den Unternehmenswert mittel FCF Verfahren aus
     * Dies ist die interne Methode um nur den Unternehmenswert auszurechnen
     *
     * @param parameter enthält die Parameter aller Perioden
     * @param intermediate enthält die Zwischenergebnisse (Stichwort Iteration)
     * @param periode beschreibt die Periode, für die der Unternehmenswert berechnet werden soll
     * @return gibt den Unternehmenswert als double Wert zurück
     */
    private static double calculateUWert(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode){
        return calculateGK(parameter,intermediate,periode) - parameter.getFK()[periode];
    }


    /**
     * Berechnet den Unternehmenswert mittels der Free-Cashflow-Methode aus
     * 
     * @param parameter enthält die Daten, welche für das FCF-Verfahren benötigt werden
     * @return gibt den Unternehmenswert inklusive aller wichtigen Parameter als FCFResult Objekt zurück
     */
    @Override
    public FCFResult calculateUWert(final CFParameter parameter) {
        // Gibt dem Stepper mit, wie eine Iteration der Unternehmenswertberechnung bei dem FCF-Verfahren implementiert ist
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

        return new FCFResult(result.getuWert()[0],result.getuWert()[0] + parameter.getFK()[0]);
    }
}
