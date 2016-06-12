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




import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;


/**
 * Diese Klasse testet die neue Klasse Distribution und die zugehörige Füllmethode createDistributionFromPrognosis(...) in der Klasse AnalysisTimeseries
 * 
 * @author Jonathan Janke
 * 
 */
public class TestDistribution extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
	@Test

	public void testDistribution() {
		int periods = 5;
		int numberOfValueClasses = 10;
		int iterations = 10000;
		double intervalLength = 9999;
		double minValue = 0;
		double maxValue = 99990;
		double [][] prognosis = new double [periods][iterations];
		double testValue;
		
		for (int h=0; h<periods; h++) {
			testValue =0;
			for (int i=0; i<iterations; i++) {
				prognosis [h][i]= testValue;
				testValue=testValue+10;
			}
		}
		
		AnalysisTimeseries at = new AnalysisTimeseries();
		Distribution dist;
		dist = at.createDistributionFromPrognosis(prognosis, numberOfValueClasses);
		
		assertEquals(intervalLength, dist.getIntervalLength());
		assertEquals(minValue, dist.getMinValue());
		assertEquals(maxValue, dist.getMaxValue());
		for (int i=0; i<dist.getValues()[0].length; i++) {
			logger.debug("Wertebereich: " + dist.getIntervalStartValues()[i] + " - " +(dist.getIntervalStartValues()[i]+dist.getIntervalLength()) + ": " +  dist.getValues()[0][i]);
		}
	}

}
