package dhbw.ka.mwi.businesshorizon2.cf;

@FunctionalInterface
public interface EKKostVerschCalculator {

	/**
	 * Interface Methode um die verschiedenen Methoden zur EK Kosten Berechnung
	 * Dient der vereinheitlichung aller Methoden
	 * 
	 * @param parameter übernimmt alle für die 
	 * @param intermediate benötigt die zwischengespeicherten Ergebnisse zur besseren Berechnung der EK Kosten (Stichwort Iteration)
	 * @param periode entspricht der Periode für die die EK Kosten berechnet werden sollen
	 * @return gibt die EK Kosten als double Wert zurück
	 */
    double calculateEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode);

}
