package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import static org.junit.Assert.*;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Trendgerade;

public class TestTideCalculator {

	@Test
	public void test() {
		double [] initialTimeseries = {1,2,3,4,5,6};
		
		Trendgerade trend = new Trendgerade (initialTimeseries);
		AnalysisTimeseries at = new AnalysisTimeseries();
		
		
		double [] reducedTimeseries = at.reduceTide(initialTimeseries);
		
		double [][] reducedTimeseries2D = new double [initialTimeseries.length][10];
		double [][] initialTimeseries2D = new double [initialTimeseries.length][10];
		for (int i=0; i<initialTimeseries.length; i++) {
			for (int j=0; j<initialTimeseries2D[i].length; j++) {
				initialTimeseries2D[i][j] = i+1;
				reducedTimeseries2D[i][j] = reducedTimeseries[i];
			}
		}
		
		
		double [][] resolvedTimeseries = at.addTide(initialTimeseries, reducedTimeseries2D);
	
		assertArrayEquals(initialTimeseries2D, resolvedTimeseries);
	}

}
