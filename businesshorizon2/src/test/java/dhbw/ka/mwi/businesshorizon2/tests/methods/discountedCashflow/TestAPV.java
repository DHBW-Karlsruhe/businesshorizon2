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

package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import junit.framework.TestCase;

/**
 * Diese Klasse stellt den jUnit-Test der Klasse APV dar
 * 
 * @author Thomas Zapf, Markus Baader
 * 
 */
public class TestAPV extends TestCase {

	private static final boolean INCLUDE_IN_CALCULATION = true;
	private static final double CORPORATE_AND_SOLITARY_TAX = 0.15825;
	private static final double BORROWING_COSTS_WITHOUT_TAXES = 0.080;
	private static final double EQUITY_COSTS_WITHOUT_TAXES = 0.09969137;
	private static final double TRADE_TAX = 0.140;
	private static final double BUSINESS_TAX = 0.75 * TRADE_TAX + CORPORATE_AND_SOLITARY_TAX;
	private static final double PERSONAL_TAX_RATE = 0.26375;
	private static final double BORROWING_COSTS_AFTER_TAXES = BORROWING_COSTS_WITHOUT_TAXES * (1 - PERSONAL_TAX_RATE);
	private static final double EQUITY_COSTs_AFTER_TAXES = EQUITY_COSTS_WITHOUT_TAXES * (1 - PERSONAL_TAX_RATE);
	private static final Logger logger = Logger.getLogger("TestAPV.class");

	@Test
	public void testAPV() {
		double[] cashflow = new double[5];
		double[] interestBearingDebtCapital = new double[5];
		Szenario szenario = new Szenario(EQUITY_COSTS_WITHOUT_TAXES, BORROWING_COSTS_WITHOUT_TAXES, TRADE_TAX,
				CORPORATE_AND_SOLITARY_TAX, PERSONAL_TAX_RATE, INCLUDE_IN_CALCULATION);
		double expectedResult = 1075.24;
		double result;

		cashflow[0] = 0.0;
		cashflow[1] = 138.61;
		cashflow[2] = 202.31;
		cashflow[3] = 174.41;
		cashflow[4] = 202.52;

		interestBearingDebtCapital[0] = 1260.0;
		interestBearingDebtCapital[1] = 1320.0;
		interestBearingDebtCapital[2] = 1330.0;
		interestBearingDebtCapital[3] = 1400.0;
		interestBearingDebtCapital[4] = 0.0;

		APV apv = new APV();
		result = apv.calculateValues(cashflow, interestBearingDebtCapital, szenario);

		result = Math.round(100.0 * result) / 100.0;
		assertEquals("Result of APV is not valid.", expectedResult,result);
	}

	@Test
	public void testCalculateTaxShield() {
		
		double[] interestBearingDebtCapital = new double[5];
		
		interestBearingDebtCapital[0] = 1260.0;
		interestBearingDebtCapital[1] = 1320.0;
		interestBearingDebtCapital[2] = 1330.0;
		interestBearingDebtCapital[3] = 1400.0;
		interestBearingDebtCapital[4] = 1400.0;
		
		APV apv = new APV();
		double result = apv.calculateTaxShield(interestBearingDebtCapital, BORROWING_COSTS_AFTER_TAXES, BUSINESS_TAX, PERSONAL_TAX_RATE, BORROWING_COSTS_WITHOUT_TAXES);

		double expectedResult = 364.48;
		result = Math.round(100.0 * result) / 100.0;
		assertEquals("TaxShield is not calculated correctly", expectedResult, result);
	}

	@Test
	public void testCalculateDebtFreeCompanyValue() {
		double[] cashflow = new double[5];
		
		cashflow[0] = 0.0;
		cashflow[1] = 138.61;
		cashflow[2] = 202.31;
		cashflow[3] = 174.41;
		cashflow[4] = 202.52;
		
		APV apv = new APV();
		double result = apv.calculateDebtFreeCompanyValue(cashflow, PERSONAL_TAX_RATE, EQUITY_COSTs_AFTER_TAXES);
		
		double expectedResult = 1970.77;
		result = Math.round(100.0 * result) / 100.0;
		assertEquals("Debt-Free Company Value is not calculated correctly", expectedResult, result);
	}

}
