package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import static org.junit.Assert.*;

import org.apache.jasper.tagplugins.jstl.ForEach;
import org.junit.Test;

public class TestAutokovarianz {

	@Test
	public void testBerechneAutokovarianz() throws Exception {
		
		int n = 4;
		double [] testZeitreihe = new double[n];
		testZeitreihe[0] = 0;
		testZeitreihe[1] = 1;
		testZeitreihe[2] = 2;
		testZeitreihe[3] = 3;
		
		for (int i =0; i<testZeitreihe.length; i++) {
			System.out.println("Zeitreihe["+i+"]: " + testZeitreihe);
		}
				
		int i_TestLag = 1;
		System.out.println("Lag: " + i_TestLag);
		
		//double db_TestAutocovariance = 0;
		//System.out.println("Autovarianz: " + db_TestAutocovariance);
		
		AnalysisTimeseries atTest = new AnalysisTimeseries();		
		System.out.println("Autokovarianz: " + atTest.calculateAutocovariance(testZeitreihe, i_TestLag));
		
	}

}
