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

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import junit.framework.TestCase;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestModellparameter extends TestCase {
	
	@Ignore @Test
	public void testModellparameter() {
		
		double [] cashflows = {1002323,33256673,44,55,66};
		
		double[] expectedParmas = {-1.09253751,-0.790322469, -0.651577395, -0.488155996, -0.215330237};
		
		
		AnalysisTimeseries at = new AnalysisTimeseries();
		
		//try {
			//matrixErgebnis = at.berechneModellparameter(autokovarianzVorgabe,p)  ;
			double[] parameters = at.calculateModelParameters(at.createMatrix(cashflows), at.calculateAutocorrelations(cashflows));
		//} catch (StochasticMethodException e) {
		//	logger.debug(e.getMessage());
		//}
			
			for (double d : parameters) {
				System.out.println("actualparams: "+d);
			}
			
			for (double d : expectedParmas) {
				System.out.println("expectedparams: "+d);
			}
			
			/**
			 * Todo: Wieder aktivieren
			 */
			//Assert.assertArrayEquals(expectedParmas, parameters, 0.1); 
			
			
		
		}

	}
