/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * @author Annika Weis
 * @date 29.12.2013
 * 
 */
public class APV extends AbstractDeterministicMethod {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger("APV.class");
	private static final long serialVersionUID = 3577122037488635230L;
	private double uwsteuerfrei;
	private double steuervorteile;
	private double fremdkapital;

	// private double unternehmenssteuer;
	// private double persönlicherSteuersatz;
	// private double fremdkapitalKostenVorSteuer;
	// private double fremdkapitalKostenNachSteuer;
	// private double eigenkapitalKostenVorSteuer;
	// private double eigenkapitalKostenNachSteuer;

	@Override
	public String getName() {

		return "Adjusted-Present-Value (APV)";
	}

	@Override
	public int getOrderKey() {

		return 5;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	@Override
	public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}

	/**
	 * @author Thomas Zapf, Markus Baader
	 * @param cashflows
	 * @param verzinsliches_FK
	 * @param aufbereitete_werte
	 * @param zins_fk_vor_steuer
	 * @return
	 */
	// "APV - Phasenmodell
	// (ohne persönliche Steuern, ohne Präferenzen, konstante Finanzpolitik?)"
	// 4 Periode: Periode 1, 2, 3, 4ff
	// cashflows 1,2,3,4,5..
	// verzinsliches FK 0,1,2,3,4,5...
	// Eingabe vor Steuern
	public double calculateValues(double[] cashflows, double[] verzinsliches_FK,
			Szenario szenario) {

//		this.calculateTaxes(szenario);

		double persönlicherSteuersatz = 0.26375;
		double unternehmenssteuer = 0.75 * szenario.getBusinessTax() + szenario.getCorporateAndSolitaryTax();
		double eigenkapitalKostenVorSteuer = szenario.getRateReturnEquity();
		double fremdkapitalKostenVorSteuer = szenario.getRateReturnCapitalStock();
		double eigenkapitalKostenNachSteuer = eigenkapitalKostenVorSteuer * (1 - persönlicherSteuersatz);
		double fremdkapitalKostenNachSteuer = fremdkapitalKostenVorSteuer * (1 - persönlicherSteuersatz);
		
		double unverschuldetes_unternehmen = wert_unverschuldetes_unternehmen(
				cashflows, persönlicherSteuersatz, eigenkapitalKostenNachSteuer);
		logger.info("unv. Unternehmen: " + unverschuldetes_unternehmen);
		
		double tax_shield = tax_shield(verzinsliches_FK,
				fremdkapitalKostenNachSteuer, unternehmenssteuer,
				persönlicherSteuersatz, fremdkapitalKostenVorSteuer);
		logger.info("tax Shield: " + tax_shield);

		double fk = verzinsliches_FK[0];

		double unternehmenswert = unverschuldetes_unternehmen + tax_shield - fk;
		logger.info(unternehmenswert);
		return unternehmenswert;
	}

	// Wert des Unverschuldeten Unternehmens
	public double wert_unverschuldetes_unternehmen(double[] cashflows,
			double persönlicher_steuersatz, double ek_kosten_nach_steuer) {

		double cf_nach_steuer;
		double barwert;
		double summe = 0;

		// Endlichkeit --> Perioden -1
                double basis;
                double potenz;
		for (int i = 0; i < (cashflows.length - 1); i++) {
			cf_nach_steuer = cashflows[i] * (1 - persönlicher_steuersatz);
                        basis = ek_kosten_nach_steuer + 1.0;
			potenz = Math.pow(basis, i);
                        barwert = (cf_nach_steuer / potenz);
			summe = summe + barwert;
		}

		// Unendlichkeit
		cf_nach_steuer = cashflows[cashflows.length - 1]
				* (1.0 - persönlicher_steuersatz);
		barwert = (cf_nach_steuer / (ek_kosten_nach_steuer * Math.pow(
				(1.0 + ek_kosten_nach_steuer), (cashflows.length - 2))));
		summe = summe + barwert;

		return summe;
	}

//	public void calculateTaxes(Szenario szenario) {
//		// TODO Noch keine Eingabemöglichkeit - Abgeltungssteuer zzgl. Soli
//		// OptionalesFeld - Wenn Leer --> 0
//		persönlicherSteuersatz = 0.26375;
//		unternehmenssteuer = 0.75 * szenario.getBusinessTax() / 100
//				+ szenario.getCorporateAndSolitaryTax() / 100;
//		eigenkapitalKostenVorSteuer = szenario.getRateReturnEquity() / 100;
//		;
//		fremdkapitalKostenVorSteuer = szenario.getRateReturnCapitalStock() / 100;
//		eigenkapitalKostenNachSteuer = eigenkapitalKostenVorSteuer
//				* (1 - persönlicherSteuersatz);
//		fremdkapitalKostenNachSteuer = fremdkapitalKostenVorSteuer
//				* (1 - persönlicherSteuersatz);
//		;
//	}

	// Tax Shield ausrechnen
	public double tax_shield(double[] verzinsliches_FK,
			double zins_fk_nach_steuer, double unternehmenssteuersatz,
			double persönlicher_steuersatz, double zins_fk_vor_steuer) {

		double summe = 0;
		// Zinsen bezüglich verzinsliches FK aus der Vorperiode
		double zinsen;
		double tax_shield_vor;
		double tax_shield_nach;
		double barwert;

		// Endlichkeit
                double basis;
                double exponent;
		for (int i = 0; i < (verzinsliches_FK.length - 2); i++) {
			zinsen = verzinsliches_FK[i] * zins_fk_vor_steuer;
			tax_shield_vor = zinsen * unternehmenssteuersatz;
			tax_shield_nach = tax_shield_vor * (1.0 - persönlicher_steuersatz);
			basis = 1.0 + zins_fk_nach_steuer;
                        exponent = i+1;
                        barwert = tax_shield_nach / (Math.pow(basis, exponent ));
			summe = summe + barwert;
		}

		// Unendlichkeit
		zinsen = verzinsliches_FK[verzinsliches_FK.length - 2]
				* zins_fk_vor_steuer;
		tax_shield_vor = zinsen * unternehmenssteuersatz;
		tax_shield_nach = tax_shield_vor * (1 - persönlicher_steuersatz);
		barwert = tax_shield_nach
				/ (zins_fk_nach_steuer * (Math.pow((1 + zins_fk_nach_steuer),
						verzinsliches_FK.length - 2)));
		summe = summe + barwert;

		return summe;
	}

	// /**
	// * @author Annika Weis
	// * @param double[] cashflow
	// * @param double[] fremdkapital,
	// * @param Szenario
	// * szenario
	// * @return double unternehemnswert
	// */
	// public double calculateValues(double[] cashflow, double[] fremdkapital,
	// Szenario szenario) {
	//
	// double gk = 0;
	// double v = 0;
	// double unternehmenswert = 0;
	// double sSteuersatz;
	// double sKS;
	// double sZinsen;
	// double sEK;
	//
	// Double first_period_cashflow = null;
	// Double first_period_fremdkapital = null;
	// Double period_cashflow;
	// Double period_fremdkapital;
	// Double lastPeriod_cashflow = null;
	// Double lastPeriod_fremdkapital = null;
	// double jahr = 1;
	//
	// sKS = szenario.getCorporateAndSolitaryTax() / 100;
	// sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
	// sEK = szenario.getRateReturnEquity() / 100;
	// sZinsen = szenario.getRateReturnCapitalStock() / 100;
	//
	// for (int durchlauf = 0; durchlauf < cashflow.length; durchlauf++) {
	// period_cashflow = cashflow[durchlauf];
	// period_fremdkapital = fremdkapital[durchlauf];
	//
	// if (durchlauf == 0) { // Basisjahr
	// first_period_cashflow = cashflow[durchlauf];
	// first_period_fremdkapital = fremdkapital[durchlauf];
	// }
	//
	// // } else if (durchlauf + 1 == cashflow.length) { // letztes Jahr wird
	// // // nach der Schleife
	// // // extra berechnet
	// //
	// // }
	// else {
	// gk += abzinsen(cashflow[durchlauf], sEK, durchlauf);
	// v += (sSteuersatz * sZinsen * fremdkapital[durchlauf - 1])
	// / Math.pow(1 + sZinsen, durchlauf);
	//
	// }
	//
	// lastPeriod_cashflow = period_cashflow;
	// lastPeriod_fremdkapital = period_fremdkapital;
	//
	// jahr = durchlauf;
	// }
	//
	// // // Jahr -1, denn im letzten Durchlauf wird von der Schleife 1 addiert
	// // jahr = jahr - 1;
	// //
	// // // Berechnung des letzten Jahres
	// // gk = gk + lastPeriod_cashflow / (sEK * Math.pow(1 + sEK, jahr));
	// // v = v + (sSteuersatz * sZinsen * lastPeriod_fremdkapital)
	// // / (sZinsen * Math.pow(1 + sZinsen, jahr));
	//
	// // Unternehmenswert gesamt berechnen
	// unternehmenswert = gk + v - first_period_fremdkapital;
	// this.setUwsteuerfrei(gk);
	// this.setSteuervorteile(v);
	// this.setFremdkapital(first_period_fremdkapital);
	// logger.debug("Unternehmenswert: " + unternehmenswert);
	// return unternehmenswert;
	// }
	//
	// /**
	// * @author Annika Weis
	// * @param wert
	// * @param zinssatz
	// * @param jahre
	// * @return Double, abgezinster Wert
	// */
	// private double abzinsen(double wert, double zinssatz, int jahre) {
	// return wert / Math.pow(1 + zinssatz, jahre);
	// }

	/**
	 * @author Marcel Rosenberger
	 */
	public double getUwsteuerfrei() {
		return uwsteuerfrei;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setUwsteuerfrei(double uwsteuerfrei) {
		this.uwsteuerfrei = uwsteuerfrei;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public double getSteuervorteile() {
		return steuervorteile;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setSteuervorteile(double steuervorteile) {
		this.steuervorteile = steuervorteile;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public double getFremdkapital() {
		return fremdkapital;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setFremdkapital(double fremdkapital) {
		this.fremdkapital = fremdkapital;
	}

}
