package dhbw.ka.mwi.businesshorizon2.cf;

@FunctionalInterface
public interface EKKostVerschCalculator {

	/**
	 * Interface Methode für die verschiedenen Verfahren zur EK-Kosten-Berechnung
	 * Dient der Vereinheitlichung aller Verfahren
	 * 
	 * @param parameter übernimmt alle Parameter, die für die Unternehmenswertberechnung benötigt werden
	 * @param intermediate speichert die zwischengespeicherten Ergebnisse zur besseren Berechnung der EK-Kosten (Stichwort Iteration)
	 * @param periode entspricht der Periode für den die EK-Kosten berechnet werden sollen
	 * @return gibt die EK-Kosten als double Wert zurück
	 */
    double calculateEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode);

}
