package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

public class TestCalculateAsDistribution {
	
	private static final Logger logger = Logger.getLogger(TestCalculateAsDistribution.class);

	private static final boolean INCLUDE_IN_CALCULATION = true;
	private static final double CORPORATE_AND_SOLITARY_TAX = 0.15825;
	private static final double BORROWING_COSTS_WITHOUT_TAXES = 0.080;
	private static final double EQUITY_COSTS_WITHOUT_TAXES = 0.09969137;
	private static final double TRADE_TAX = 0.140;
	private static final double PERSONAL_TAX_RATE = 0.26375;

	@Test
	public void test() {
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] zeitreihe = {138.61, 202.31, 174.41, 202.52};
		double [] initialInterestBearingDebtCapital = {1260.0,1320.0,1330.0,1400.0};
		int p=3;
		int zuberechnendePerioden = 1;
		int durchlaeufe = 10000;
		Szenario scenario = new Szenario(EQUITY_COSTS_WITHOUT_TAXES, BORROWING_COSTS_WITHOUT_TAXES, TRADE_TAX,
				CORPORATE_AND_SOLITARY_TAX, PERSONAL_TAX_RATE, INCLUDE_IN_CALCULATION);
		
		try {
			Distribution dist = at.calculateAsDistribution(zeitreihe, initialInterestBearingDebtCapital, p, zuberechnendePerioden, durchlaeufe, scenario, null);
			logger.info("Distribution values:");
			for (int i=0; i<dist.getIntervalStartValues().length; i++) {
				logger.info(dist.getValueRange(i)+ ": " + dist.getValues()[i]);
			}
			
		} catch (Exception e) {
			logger.info("CATCH: " + e.getMessage() + "; Cause: " + e.getCause() + "; " + e.getStackTrace());
		}
	}
}
