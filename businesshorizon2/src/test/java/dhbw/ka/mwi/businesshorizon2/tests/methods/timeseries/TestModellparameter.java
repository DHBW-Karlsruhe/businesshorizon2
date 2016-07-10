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

package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import junit.framework.TestCase;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in
 * der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestModellparameter extends TestCase {

	@Test
	public void testModellparameter() {

		double[] cashflows = {22, 33, 44, 55, 66 };

		double[] expectedParmas = { -1.09253751, -0.790322469, -0.651577395, -0.488155996, -0.215330237 };
		double [] expectedParams = {1, 3.5E-16, -1.7E-16, 1.1E-16, -5.6E-17};
		
		AnalysisTimeseries at = new AnalysisTimeseries();

		// try {
		// matrixErgebnis = at.berechneModellparameter(autokovarianzVorgabe,p) ;
		double[] parameters = at.calculateModelParameters(at.createMatrix(cashflows), at.calculateAutocorrelations(cashflows));
		// } catch (StochasticMethodException e) {
		// logger.debug(e.getMessage());
		// }

		/*for (double d : parameters) {
			System.out.println("actualparams: " + d);
		}

		for (double d : expectedParams) {
			System.out.println("expectedparams: " + d);
		}*/

		Assert.assertArrayEquals(expectedParams, parameters, 0.1);

	}

}
