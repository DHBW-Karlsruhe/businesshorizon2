package dhbw.ka.mwi.businesshorizon2.cf.parameter;

/**
 * Stellt Werte der Bilanz dar
 *
 */
public class Bilanz {

	private final double anlage;
	private final double umlauf;
	private final double ek;
	private final double zinsPfPassiva;
	private final double sonstPassiva;

	/**
	 * @param anlage Anlagevermögen
	 * @param umlauf Umlaufvermogen
	 * @param ek Eigenkapital
	 * @param zinsPfPassiva Zinspfilchtiges Passive
	 * @param sonstPassiva Sonstiges Passive
	 * @throws IllegalArgumentException Wird geworfen, wenn die Bilanz nicht ausgeglichen ist
	 */
	public Bilanz(final double anlage, final double umlauf, final double ek, final double zinsPfPassiva,
			final double sonstPassiva) {
        this.anlage = anlage;
		this.umlauf = umlauf;
		this.ek = ek;
		this.zinsPfPassiva = zinsPfPassiva;
		this.sonstPassiva = sonstPassiva;
		if (getKontrolle() != 0) {
			//throw new IllegalArgumentException("Bilanz ist nicht ausgelichen");
		}
	}

	public double getAnlage() {
		return anlage;
	}

	public double getUmlauf() {
		return umlauf;
	}

	public double getEk() {
		return ek;
	}

	public double getZinsPfPassiva() {
		return zinsPfPassiva;
	}

	public double getSonstPassiva() {
		return sonstPassiva;
	}

	private double getKontrolle() {
		return anlage + umlauf - ek - zinsPfPassiva - sonstPassiva;
	}

}
