package dhbw.ka.mwi.businesshorizon2.cf.parameter;

/**
 * GUV und Bilanz für eine Periode
 *
 */
public class CFPeriode {

	private final GUV guv;
	private final Bilanz bilanz;

	/**
	 * @param guv GUV
	 * @param bilanz Bilanz
	 */
	public CFPeriode(final GUV guv, final Bilanz bilanz) {
        this.guv = guv;
		this.bilanz = bilanz;
	}

	public GUV getGuv() {
		return guv;
	}

	public Bilanz getBilanz() {
		return bilanz;
	}

}
