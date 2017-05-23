package dhbw.ka.mwi.businesshorizon2.cf;

import java.util.HashMap;

/**
 * Wird intern zum Caching von Ergebnissen verwendet
 * @param <T> Enum welcher die zur Verfügung stehenden Methoden bescheibt
 */
public class MethodMap<T extends Enum<?>> extends HashMap<String, Double> {

	private static final long serialVersionUID = 1L;

	/**
	 * Ermittelt einen Schlüssel mit dem Ergebnisse in die Map gespeichert und gelesen werden können
	 * @param method Methode
	 * @param parameter Parameter der Methode, es wird nur int unterstützt
	 * @return Schlüssel, der für den Zugriff der Map verwendet werden kann
	 */
	public String getMethodKey(final T method, final int... parameter) {
		String key = String.valueOf(method.ordinal());

		for (final int param : parameter) {
			key = key + ';' + param;
		}

		return key;
	}
}
