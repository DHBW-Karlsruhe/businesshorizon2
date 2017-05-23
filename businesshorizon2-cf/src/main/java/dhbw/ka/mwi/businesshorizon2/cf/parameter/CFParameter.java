package dhbw.ka.mwi.businesshorizon2.cf.parameter;

import java.util.Collections;
import java.util.List;

/**
 * Parameter für die Cashflow Ermittlung
 *
 */
public class CFParameter {

	private final double ekKosten;

	private final double perSteuer;

	private final double jahresUeberschuss;

	private final double strukturbilanzen;

	private final double zinsaufwand;

	private final List<CFPeriode> perioden;

	/**
	 * @param ekKosten Eigenkapitalkosten (unverschuldet)
	 * @param perSteuer Persönliche Steuern
	 * @param jahresUeberschuss Jahresüberschuss
	 * @param strukturbilanzen Struktur-Bilanzen
	 * @param zinsaufwand Zinsaufwand
	 * @param perioden GUV und Bilanzen der Perioden
	 */
	public CFParameter(final double ekKosten, final double perSteuer, final double jahresUeberschuss,
			final double strukturbilanzen, final double zinsaufwand, final List<CFPeriode> perioden) {
		this.ekKosten = ekKosten;
		this.perSteuer = perSteuer;
		this.jahresUeberschuss = jahresUeberschuss;
		this.strukturbilanzen = strukturbilanzen;
		this.zinsaufwand = zinsaufwand;
		this.perioden = Collections.unmodifiableList(perioden);
	}

	public double getEkKosten() {
		return ekKosten;
	}

	public double getPerSteuer() {
		return perSteuer;
	}

	public double getErtragsSteuer() {
		return 0.5 * jahresUeberschuss * (1 - strukturbilanzen) + strukturbilanzen;
	}

	public double getZinsaufwand() {
		return zinsaufwand;
	}

	public double getEKKostenUnverschuldet() {
		return ekKosten * (1 - 0.5 * perSteuer);
	}

	public GUV getGuv(final int periode) {
		return perioden.get(periode).getGuv();
	}

	public Bilanz getBilanz(final int periode) {
		return perioden.get(periode).getBilanz();
	}

	public boolean isLastPeriode(final int periode) {
		return periode + 2 == perioden.size();
	}

	public double getJahresUeberschuss() {
		return jahresUeberschuss;
	}

	public double getStrukturbilanzen() {
		return strukturbilanzen;
	}

}
