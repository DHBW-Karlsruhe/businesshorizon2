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

import java.util.*;

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
		//logged results might have to be ignored with high test number
		testingPart(0);
		testingPart(1);
		testingPart(10);
		testingPart(10000);
		testingPart(100000);
		testingPart (100);
	}
	
	public void testingPart(int numberOfValueClasses) {
	int iterations = 10000;
	
	double minValue = 0;
	double maxValue = 99990;
	double intervalLength;
	if (numberOfValueClasses>0) intervalLength = maxValue/numberOfValueClasses;
	else intervalLength = maxValue/1;
	double [] prognosisDeterministic = new double [iterations];
	double [] prognosisStochastic = new double [iterations];
	double testValueDeterministic = 0;
	double testValueStochastic;
	Random gaussian = new Random();
	 
	for (int i=0; i<iterations; i++) {
		prognosisDeterministic [i]= testValueDeterministic;
		testValueDeterministic=testValueDeterministic+10;
	}
	for (int i=0; i<iterations; i++) {
		testValueStochastic=5000+gaussian.nextGaussian()* 1000;
		prognosisStochastic [i]= testValueStochastic;
	}
	
	Distribution distDeterministic = new Distribution(numberOfValueClasses, prognosisDeterministic);
	Distribution distStochastic = new Distribution(numberOfValueClasses, prognosisStochastic);

	
	assertEquals(intervalLength, distDeterministic.getIntervalLength());
	assertEquals(minValue, distDeterministic.getMinValue());
	assertEquals(maxValue, distDeterministic.getMaxValue());
	logger.debug("Die folgenden Werte sollten genau gleichverteilt sein:");
	for (int i=0; i<distDeterministic.getValues().length; i++) {
		logger.debug("Wertebereich: " + distDeterministic.getIntervalStartValues()[i] + " - " +(distDeterministic.getIntervalStartValues()[i]+distDeterministic.getIntervalLength()) + ": " +  distDeterministic.getValues()[i]);
	}
	logger.debug("\nIn den folgenden Werten muss geguckt werden, ob diese in etwa einer Normalverteilung ähneln:");
	for (int i=0; i<distStochastic.getValues().length; i++) {
		logger.debug("Wertebereich: " + distStochastic.getIntervalStartValues()[i] + " - " +(distStochastic.getIntervalStartValues()[i]+distStochastic.getIntervalLength()) + ": " +  distStochastic.getValues()[i]);
	}
}

}
