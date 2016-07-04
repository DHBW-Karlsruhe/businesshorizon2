package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;

public class TestCalculateAsDistribution {
	
	private static final Logger logger = Logger.getLogger(TestCalculateAsDistribution.class);

	@Test
	public void test() {
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] zeitreihe = {7, 9, 5, 14, 6, 8};
		double [] initialInterestBearingDebtCapital = {4,6,5,6,8,9};
		int p=5;
		int zuberechnendePerioden = 3;
		int durchlaeufe = 10000;
		
		try {
			Distribution dist = at.calculateAsDistribution(zeitreihe, initialInterestBearingDebtCapital, p, zuberechnendePerioden, durchlaeufe, null, null);
			logger.info("Distribution values:");
			for (int i=0; i<dist.getIntervalStartValues().length; i++) {
				logger.info(dist.getValueRange(i)+ ": " + dist.getValues()[i]);
			}
			
		} catch (Exception e) {
			logger.info("CATCH: " + e.getMessage());
		}
	}
}
