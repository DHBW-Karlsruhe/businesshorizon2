package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;
import org.apache.log4j.Logger;

public class calculateAsDistribution {
	
	private static final Logger logger = Logger.getLogger(calculateAsDistribution.class);

	@Test
	public void test() {
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] zeitreihe = {7, 9, 5, 14, 6, 8};
		double [] initialInterestBearingDebtCapital = {4,5,6,7,8,9};
		int p=5;
		int zuberechnendePerioden = 3;
		int durchlaeufe = 10000;
		
		try {
			Distribution dist = at.calculateAsDistribution(zeitreihe, initialInterestBearingDebtCapital, p, zuberechnendePerioden, durchlaeufe, null);
			logger.info(Arrays.toString(dist.getValues()));
		} catch (Exception e) {
			logger.info("CATCH: " + e.getMessage());
		}
	}
}
