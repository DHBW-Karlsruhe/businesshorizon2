package dhbw.ka.mwi.businesshorizon2.cf.parameter;

/**
 * Stellt Werte der GUV dar
 *
 */
public class GUV {

	private final double gesamtleistung;
	private final double opkosten;
	private final double abschr;

	/**
	 * @param gesamtleistung Gesamtleistung
	 * @param opkosten Operative Kosten
	 * @param abschr Abschreibungen
	 */
	public GUV(final double gesamtleistung, final double opkosten, final double abschr) {
        this.gesamtleistung = gesamtleistung;
		this.opkosten = opkosten;
		this.abschr = abschr;
	}

	public double getGesamtleistung() {
		return gesamtleistung;
	}

	public double getOpkosten() {
		return opkosten;
	}

	public double getAbschr() {
		return abschr;
	}

}
