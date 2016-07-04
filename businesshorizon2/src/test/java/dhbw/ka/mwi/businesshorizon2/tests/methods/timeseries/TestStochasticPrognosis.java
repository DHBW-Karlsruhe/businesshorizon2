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

import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;




import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;


/**
 * Diese Klasse testet die neue Klasse Distribution und die zugehörige Füllmethode createDistributionFromPrognosis(...) in der Klasse AnalysisTimeseries
 * 
 * @author Jonathan Janke
 * 
 */
public class TestStochasticPrognosis extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
	@Test

	public void testStochasticPrognosis() {

		//double intervalLength = 9999;
		//double minValue = 0;
		//double maxValue = 99990;
		
		//testPrognosis (5, 10000, 10, false);
		//testPrognosis (10, 10000, 10, false);
		//testPrognosis (5, 10, 10, false);
		testPrognosis (5, 100000, 10, true, 150000, 5000);
	}
		
	private void testPrognosis(int i, int j, int k, boolean b) {
		if (!b) testPrognosis(i,j,k,b,0,0);
		else logger.debug("ERROR: Can only test stochastic method with deviation != 0)");;
	}

	public void testPrognosis (int periods, int iterations, int numberOfValueClasses, boolean stochastic, double avg, double deviation) {
		double [][] prognosis = new double [periods][iterations];
		double testValue;
		Random gaussian = new Random();
		
		//interestbearingdebtcapital wird unten mit dummy Werten gefüllt (1 an jeder Stelle)
		double [] interestBearingDebtCapital = new double [periods];
		Szenario scenario = new Szenario(10, 20, 30, 40, false);
			
		for (int h=0; h<periods; h++) {
			testValue= 0;
			
			interestBearingDebtCapital [h] = 1;
			for (int i=0; i<iterations; i++) {
				if (stochastic) {
					testValue = avg + gaussian.nextGaussian()*deviation;
				} else {
					testValue=testValue+10;
				}
				prognosis [h][i]= testValue;
			}
		}
		
		AnalysisTimeseries at = new AnalysisTimeseries();
		Distribution dist = at.createStochasticPrognosis(prognosis, numberOfValueClasses, interestBearingDebtCapital, scenario);
		
		logger.debug("distlaenge: " + dist.getValues().length);
		for (int i=0; i<dist.getValues().length; i++) {
			logger.info("values between " + dist.getValueRange(i) + ": " + dist.getValues()[i]);
		}
		/*assertEquals(intervalLength, dist.getIntervalLength());
		assertEquals(minValue, dist.getMinValue());
		assertEquals(maxValue, dist.getMaxValue());
		for (int i=0; i<dist.getValues()[0].length; i++) {
			logger.debug("Wertebereich: " + dist.getIntervalStartValues()[i] + " - " +(dist.getIntervalStartValues()[i]+dist.getIntervalLength()) + ": " +  dist.getValues()[0][i]);
		}
		*/
		}

}
