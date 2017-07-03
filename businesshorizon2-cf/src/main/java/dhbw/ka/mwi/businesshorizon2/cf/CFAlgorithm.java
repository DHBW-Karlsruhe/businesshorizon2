package dhbw.ka.mwi.businesshorizon2.cf;

@FunctionalInterface
public interface CFAlgorithm<T extends CFResult> {
	
	/**
	 * Interface Methode für die Berechnung eines Unternehmenswert
	 * @param parameter entspricht den für die Verfahren benötigten Daten
	 * @return Unternehmenswert und gegebenenfalls zusätzliche Informationen zu der Berechnung
	 */
    T calculateUWert(CFParameter parameter);
}
