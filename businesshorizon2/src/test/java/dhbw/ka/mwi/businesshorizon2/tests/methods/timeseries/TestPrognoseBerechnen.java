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
import org.junit.Test;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;


import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * Anmerkung2016 (JJ): Test prüft lediglich, dass Array != null ist.
 * @author Volker Maier, Jonathan Janke
 * 
 */

public class TestPrognoseBerechnen extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
		
	@Test
	public void testPrognoseBerechnen() {
		int p = 5;
		int i = 1;
		//DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(p, i);
		
		double standardabweichung = 1.8551461075783124; 
		int zuberechnendeperioden = 6;
		int durchlaeufe = 10000;
		//double mittelwert = 8.166666666666666;
		double mittelwert = 0;
		boolean isfremdkapital = true;
		double[][] prognosewerte = new double[zuberechnendeperioden][durchlaeufe];
		double[][] compareValues = new double[zuberechnendeperioden][durchlaeufe];
		
		double [] phiValues = {7/16, 9/16, 0, 0, 0};
		double [] cashflows = {7, 9, 5, 14, 6, 8};
		
		//matrixValuations.set(0,0,-1.09253751);
		//matrixValuations.set(1,0,-0.790322469);
		//matrixValuations.set(2,0,-0.651577395);
		//matrixValuations.set(3,0,-0.488155996);
		//matrixValuations.set(4,0,-0.215330237);
	
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] autokovarianzVorgabe = {8.472222222222223, -5.726851851851852, 2.407407407407408, -1.3472222222222223, 0.3981481481481479, 0.032407407407407274};
		
		
		//prognosewerte = at.prognoseBerechnen(cashflows, matrixValuations, standardabweichung, zuberechnendeperioden, durchlaeufe, p, mittelwert, isfremdkapital)  ;
		prognosewerte = at.prognoseBerechnen(cashflows, phiValues, standardabweichung, zuberechnendeperioden, durchlaeufe, p, mittelwert, isfremdkapital)  ;
		compareValues = at.prognoseBerechnen(cashflows, phiValues, 0, zuberechnendeperioden, durchlaeufe, p, 0, isfremdkapital)  ;
		//array zum Überprüfen, ob Mittelwerte der Perioden ungefähr beeinander liegen
		double averagePerPeriod[] = new double [zuberechnendeperioden];
		double averagePerPeriodCompare[] = new double [zuberechnendeperioden];
		for (int k=cashflows.length; k<prognosewerte.length; k++) {
			for (int j=0; j<prognosewerte[k].length; j++) {
				//logger.debug("prognosewerte["+ k + "][" + j + "] " + prognosewerte[k][j]);
				averagePerPeriod[k-cashflows.length]+=prognosewerte[k][j];
				averagePerPeriodCompare[k-cashflows.length]+=compareValues[k][j];
			}
			averagePerPeriod[k-cashflows.length]=averagePerPeriod[k-cashflows.length]/durchlaeufe;
			averagePerPeriodCompare[k-cashflows.length]=averagePerPeriodCompare[k-cashflows.length]/durchlaeufe;

			logger.debug("Periode: " + (k-cashflows.length));
			logger.debug("Prognose: " + averagePerPeriod[k-cashflows.length]);
			logger.debug("Vergleichswert: " + averagePerPeriodCompare[k-cashflows.length]);
			assertEquals((double)Math.round(averagePerPeriod[k-cashflows.length]),averagePerPeriodCompare[k-cashflows.length]);
		}
		
		
		
		//prüfen, ob Prognosearray rightige Länge besitzt (Dimension 1); richtige Länge, wenn bisherige Cashflows + zuberechnendePerioden enthalten sind
		assertEquals(prognosewerte.length, zuberechnendeperioden);
		//prüfen, ob Prognosearray rightige Länge besitzt (Dimension 2); richtige Länge, wenn durchlaeufe in Dimension 2 sind
		assertEquals(prognosewerte[0].length, durchlaeufe);
		assertNotNull(prognosewerte);
		
		}

	}
