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
	 * @param interestBearingDebtCapital
	 * @param aufbereitete_werte
	 * @param zins_fk_vor_steuer
	 * @return
	 */
	// "APV - Phasenmodell
	// (ohne persönliche Steuern, ohne Präferenzen, konstante Finanzpolitik)"
	public double calculateValues(double[] cashflows, double[] interestBearingDebtCapital,
			Szenario szenario) {

		double personalTaxRate = szenario.getPersonalTaxRate();
		double businessTax = 0.75 * szenario.getBusinessTax() + szenario.getCorporateAndSolitaryTax();
		double equityCostsWithoutTaxes = szenario.getRateReturnEquity();
		double borrowingCostsWithoutTaxes = szenario.getRateReturnCapitalStock();
		double equityCostsAfterTaxes = equityCostsWithoutTaxes * (1 - personalTaxRate);
		double borrowingCostsAfterTaxes = borrowingCostsWithoutTaxes * (1 - personalTaxRate);
		
		double debtFreeCompany = calculateDebtFreeCompanyValue(
				cashflows, personalTaxRate, equityCostsAfterTaxes);
		//logger.info("unv. Unternehmen: " + debtFreeCompany);
		
		double tax_shield = calculateTaxShield(interestBearingDebtCapital,
				borrowingCostsAfterTaxes, businessTax,
				personalTaxRate, borrowingCostsWithoutTaxes);
		//logger.info("tax Shield: " + tax_shield);

		double outsideCapital = interestBearingDebtCapital[0];

		double companyValue = debtFreeCompany + tax_shield - outsideCapital;
		//logger.info(companyValue);
		return companyValue;
	}

	// Wert des Unverschuldeten Unternehmens
	public double calculateDebtFreeCompanyValue(double[] cashflows,
			double personalTaxRate, double equityCostsAfterTaxes) {

		double cashflowAfterTaxes;
		double cashValue;
		double total = 0;

		// Endlichkeit --> Perioden -1
                double base;
                double potency;
		for (int i = 0; i < (cashflows.length - 1); i++) {
			cashflowAfterTaxes = cashflows[i] * (1 - personalTaxRate);
			base = equityCostsAfterTaxes + 1.0;
			potency = Math.pow(base, i);
            cashValue = (cashflowAfterTaxes / potency);
			total = total + cashValue;
		}

		// Unendlichkeit
		cashflowAfterTaxes = cashflows[cashflows.length - 1]
				* (1.0 - personalTaxRate);
		cashValue = (cashflowAfterTaxes / (equityCostsAfterTaxes * Math.pow(
				(1.0 + equityCostsAfterTaxes), (cashflows.length - 2))));
		total = total + cashValue;

		return total;
	}

	// Tax Shield ausrechnen
	public double calculateTaxShield(double[] interestBearingDebtCapital,
			double borrowingCostsAfterTaxes, double businessTax,
			double personalTaxRate, double borrowingCostsWithoutTaxes) {

		double total = 0;
		// Zinsen bezüglich verzinsliches FK aus der Vorperiode
		double interest;
		double taxShieldWithoutTaxes;
		double taxShieldAfterTaxes;
		double cashValue;

		// Endlichkeit
        double base;
        double exponent;
        
		for (int i = 0; i < (interestBearingDebtCapital.length - 2); i++) {
			interest = interestBearingDebtCapital[i] * borrowingCostsWithoutTaxes;
			taxShieldWithoutTaxes = interest * businessTax;
			taxShieldAfterTaxes = taxShieldWithoutTaxes * (1.0 - personalTaxRate);
			base = 1.0 + borrowingCostsAfterTaxes;
            exponent = i+1;
            cashValue = taxShieldAfterTaxes / (Math.pow(base, exponent ));
			total = total + cashValue;
		}

		// Unendlichkeit
		interest = interestBearingDebtCapital[interestBearingDebtCapital.length - 2]
				* borrowingCostsWithoutTaxes;
		taxShieldWithoutTaxes = interest * businessTax;
		taxShieldAfterTaxes = taxShieldWithoutTaxes * (1 - personalTaxRate);
		cashValue = taxShieldAfterTaxes
				/ (borrowingCostsAfterTaxes * (Math.pow((1 + borrowingCostsAfterTaxes),
						interestBearingDebtCapital.length - 2)));
		total = total + cashValue;

		return total;
	}

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
