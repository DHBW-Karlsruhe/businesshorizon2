package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAutokovarianz {

	@Test
	public void testBerechneAutokovarianz() throws Exception {
		
		//int n = 4;
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] testZeitreihe = {8,12,10,14,12,16};
		testZeitreihe = at.reduceTide(testZeitreihe);
		
		for (int i =0; i<testZeitreihe.length; i++) {
			System.out.println("Zeitreihe["+i+"]: " + testZeitreihe[i]);
		}
				
		int i_TestLag = 0;
		System.out.println("Lag: " + i_TestLag);
		
		//double db_TestAutocovariance = 0;
		//System.out.println("Autovarianz: " + db_TestAutocovariance);
		
		AnalysisTimeseries atTest = new AnalysisTimeseries();	
		for(int i = 0; i<testZeitreihe.length;i++){
			i_TestLag = i;
			System.out.println("Autokovarianz mit Lag "+ i_TestLag+": " + atTest.calculateAutocovariance(testZeitreihe, i_TestLag));
			System.out.println("Autokorrelation mit Lag "+ i_TestLag+": " + atTest.calculateAutocorrelation(testZeitreihe, i_TestLag));
		}
				
	}

}
