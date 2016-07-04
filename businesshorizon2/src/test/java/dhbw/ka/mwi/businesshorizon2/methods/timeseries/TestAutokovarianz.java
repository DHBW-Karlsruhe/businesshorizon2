package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAutokovarianz {

	@Test
	public void testBerechneAutokovarianz() throws Exception {
		
		//int n = 4;
		double [] testZeitreihe = {0,1,2,3};
		
		for (int i =0; i<testZeitreihe.length; i++) {
			System.out.println("Zeitreihe["+i+"]: " + testZeitreihe[i]);
		}
				
		int i_TestLag = 1;
		System.out.println("Lag: " + i_TestLag);
		
		//double db_TestAutocovariance = 0;
		//System.out.println("Autovarianz: " + db_TestAutocovariance);
		
		AnalysisTimeseries atTest = new AnalysisTimeseries();		
		System.out.println("Autokovarianz: " + atTest.calculateAutocovariance(testZeitreihe, i_TestLag));
		
	}

}
