package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import org.junit.Assert;
import org.junit.Test;


public class TestAutokovarianz {

	@Test
	public void testBerechneAutokovarianz() throws Exception {
		
		//int n = 4;
		AnalysisTimeseries at = new AnalysisTimeseries();
		double [] testZeitreihe = {8,12,10,14,12,16};
		testZeitreihe = at.reduceTide(testZeitreihe);
		
//		Debug output
//		for (int i =0; i<testZeitreihe.length; i++) {
//			System.out.println("Zeitreihe["+i+"]: " + testZeitreihe[i]);
//		}
				
//		Debug Output
//		System.out.println("Lag: " + i_TestLag);
		
		AnalysisTimeseries atTest = new AnalysisTimeseries();	
		
//		Debug Output
//		for(int i = 0; i<testZeitreihe.length;i++){
//			i_TestLag = i;
//			System.out.println("Autokovarianz mit Lag "+ i_TestLag+": " + atTest.calculateAutocovariance(testZeitreihe, i_TestLag));
//			System.out.println("Varianz der Zeitreihe: "+ atTest.berechneVarianz(testZeitreihe));
//			System.out.println("Autokorrelation mit Lag "+ i_TestLag+": " + atTest.calculateAutocorrelation(testZeitreihe, i_TestLag));
//		}
		
		Assert.assertEquals(3.5, atTest.calculateAutocovariance(testZeitreihe, 0),0.2);
		Assert.assertEquals(-0.31, atTest.calculateAutocovariance(testZeitreihe, 1),0.2);
		Assert.assertEquals(1.96, atTest.calculateAutocovariance(testZeitreihe, 2),0.2);
		Assert.assertEquals(-1.38, atTest.calculateAutocovariance(testZeitreihe, 3),0.2);
		Assert.assertEquals(1.03, atTest.calculateAutocovariance(testZeitreihe, 4),0.2);
		Assert.assertEquals(0, atTest.calculateAutocovariance(testZeitreihe, 5),0.2);

		
	}
	

}
