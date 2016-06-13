/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C)
 * 
 * @author Jonathan Janke
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

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.CalculateTide;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * Es wurde eine neue Klasse mit _2016 geschaffen, da der bisherige Test meiner Ansicht nach falsch ist. 
 * Bisher wurde versucht, eine Trendgerade zu legen und die Zeitreihe über den Abstand zur Trendgeraden zu differenzieren.
 * 
 * bisherige Formel: Y(t) = X(t) - T(t) mit T(t) = Trendgerade
 * jetzige Formel: X'(t) = X(t) - X(t-1)
 * 
 * Für eine korrekte Versionsverwaltung sollte man langfristig eine der beiden Testmethoden löschen und die andere entsprechend umbenennen.
 * 
 * @author Jonathan Janke
 * 
 */


public class CalculateTideTest_2016 extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
	@Test
		
	public void testReduceTide() {
		double[] timeseries = new double[6];
		timeseries[0] = 130594000.00;
		timeseries[1] = 147552000.00;
		timeseries[2] = 144040000.00;
		timeseries[3] = 146004000.00;
		timeseries[4] = 154857000.00;
		timeseries[5] = 162117000.00;
		CalculateTide tide = new CalculateTide();
		timeseries = tide.reduceTide(timeseries);
		double[] results = new double[timeseries.length];
		results[0] = 0;
		results[1] = 16958000;
		results[2] = -3512000;
		results[3] = 1964000;
		results[4] = 8853000;
		results[5] = 7260000;

		System.out.println(timeseries[1]);
		System.out.println(results[1]);
		for (int i = 1; i < timeseries.length; i++) {
			logger.debug("expected: " + results[i] + "; value: "+ timeseries [i]);
			assertEquals(results[i], timeseries[i]);
		}

	}
}
