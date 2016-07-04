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

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in
 * der Klasse AnalysisTimeseries dar.
 * 
 * @author Volker Maier, Jonathan Janke
 * 
 */

// FIXME seazzle
public class TestAutokovarianz {

	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");

	@Test
	public void testAutokovarianz() {
		AnalysisTimeseries at = new AnalysisTimeseries();
		//double[] cashflows = { 7, 9, 5, 14, 6, 8 };
		double[] cashflows_symmetrisch = {8, 9, 10, 11, 12, 13};
		double[] autokovarianzVorgabe = { 8.472222222222223, -5.726851851851852, 2.407407407407408, -1.3472222222222223, 0.3981481481481479, 0.032407407407407274 };
		double[] autokovarianzReducedVorgabe = { 8.472222222222223, -5.726851851851852, 2.407407407407408, -1.3472222222222223, 0.3981481481481479, 0.032407407407407274 };
		double[] autokovarianz = new double[autokovarianzVorgabe.length];
		double[] autokovarianzreduced = new double[autokovarianzVorgabe.length];

		double[] reducedCashflows = at.reduceTide(cashflows_symmetrisch);
		// logger.debug(Arrays.toString(reducedCashflows));
		autokovarianz = at.calculateAutocovariances(cashflows_symmetrisch);
		autokovarianzreduced = at.calculateAutocovariances(reducedCashflows);

		logger.debug(Arrays.toString(autokovarianzVorgabe));
		logger.debug(Arrays.toString(autokovarianz));

		// prüft, ob Kovarianz mit Lag 0 der Varianz entspricht
		Assert.assertEquals(at.calculateAutocovariance(at.reduceTide(cashflows_symmetrisch), 0), at.berechneVarianz(cashflows_symmetrisch), 0.1);
		
		//überprüft ob Kovarianzen mit Vorgaben übereinstimmen
		Assert.assertArrayEquals(autokovarianzVorgabe, autokovarianz, 0.1);
		Assert.assertArrayEquals(autokovarianzReducedVorgabe, autokovarianzreduced, 0.1);


	}

}
