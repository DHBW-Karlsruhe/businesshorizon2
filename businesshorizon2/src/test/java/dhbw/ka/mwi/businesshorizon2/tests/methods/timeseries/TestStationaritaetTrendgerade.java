package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Trendgerade;
import junit.framework.TestCase;

/**
 * Diese Klasse dient dazu die Erstellung der Trendgerade und Überprüfung auf Stationarität zu überprüfen
 * @author: Philipp Nagel
 */
public class TestStationaritaetTrendgerade extends TestCase{
	@Test public void testTrendGerade(){
		double[] zeitreihe = {0,1,2,3,4,5};
		Trendgerade gerade = Trendgerade.getTrendgerade(zeitreihe);
		assertEquals(gerade.getM(), 1);
		assertEquals(gerade.getB(), 0);
		
		
		zeitreihe = new double[]{3,5,7};
		gerade = Trendgerade.getTrendgerade(zeitreihe);
		assertEquals(gerade.getM(), 2);
		assertEquals(gerade.getB(), 1);
		
	}
	
	@Test public void testStationaritaet(){
		
	}

}
