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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import Jama.Matrix;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

@Ignore //FIXME

public class TestModellparameter extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
		
	@Test
	public void testModellparameter() {
		int p = 5;
		int i = 1;
		//DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(p, i);
		//DoubleMatrix2D matrixErgebnis = DoubleFactory2D.dense.make(p, i);
		
		Matrix matrixValuations = new Matrix(p,i);
		Matrix matrixErgebnis = new Matrix(p, i);
		double [] cashflows = {7,9,5,14,6,8};
		
		matrixValuations.set(0,0,-1.09253751);
		matrixValuations.set(1,0,-0.790322469);
		matrixValuations.set(2,0,-0.651577395);
		matrixValuations.set(3,0,-0.488155996);
		matrixValuations.set(4,0,-0.215330237);
	
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] autokovarianzVorgabe = {8.472222222222223, -5.726851851851852, 2.407407407407408, -1.3472222222222223, 0.3981481481481479, 0.032407407407407274};
		
		//try {
			//matrixErgebnis = at.berechneModellparameter(autokovarianzVorgabe,p)  ;
			matrixErgebnis = at.calculateModelParameters(at.createMatrix(cashflows), at.calculateAutocorrelations(cashflows));
		//} catch (StochasticMethodException e) {
		//	logger.debug(e.getMessage());
		//}
		
		logger.debug(matrixValuations);
		logger.debug(matrixErgebnis);
		
			assertEquals(matrixValuations, matrixErgebnis);
		}

	}
