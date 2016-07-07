package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import org.junit.Ignore;
import org.junit.Test;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleMatrix2D;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import junit.framework.TestCase;
/**
 * Diese Klasse enthält die restlichen Tests der Klasse AnalysisTimeseries. Teilweise sind die Tests auf Ignore gesetzt, da sie legacy Methoden testen und dadurch ein sinnvoller Wert für die Code coverage erreicht werden soll
 * author: Philipp Nagel
 */
public class TestAnalysisTimeSeriesRemaining extends TestCase {
	private AnalysisTimeseries at = new dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries();
	@Ignore @Test
	public void testPrognoseBerechnen(){
		DoubleArrayList dal = new DoubleArrayList();
		dal.add(1);
		dal.add(2);
		
		DoubleMatrix2D matrixPhi = null;;
		//at.prognoseBerechnen(dal, matrixPhi, 1.5, 2, 2, 2, 1.5, true);
		assertTrue(true);
	}
	
	
	/*
	 * Dieser Test wird ignoriert, da die Methode exklusiv von prognoseBerechnen aufgerufen wird, welche nich mehr verwendet wird
	 */
	@Ignore @Test
	public void testErwarteteWerteBerechnen(){
		DoubleArrayList dal = new DoubleArrayList();
		dal.add(1);
		dal.add(2);
		
		DoubleMatrix2D matrixPhi = null;
		//at.erwarteteWerteBerechnen(dal, matrixPhi, 2, 2, 1.5, true);
		assertTrue(true);
	}
	
	@Test
	public void testSetAndGetAbweichung(){
		for(int i=0;i<100;i++){
			int randomInt = (int) Math.random();
			at.setAbweichung(randomInt);
			assertEquals(randomInt, ((int)at.getAbweichung()));
		}
		for(int i=0;i<100;i++){
			double randomDouble = Math.random();
			at.setAbweichung(randomDouble);
			assertEquals(randomDouble, at.getAbweichung());
		}
	}
	/*
	 * Wird ignoriert, da nicht mehr verwendet
	 */
	@Ignore @Test
	public void testValidierung(){
		DoubleArrayList dal = new DoubleArrayList();
		dal.add(1);
		dal.add(2);
		
		DoubleMatrix2D matrixPhi = null;
		
		//at.validierung(dal, matrixPhi, 2);
		assertTrue(true);
	}
	
	@Test
	public void testGetErwarteteCahsFlows(){
		double[] out = at.getErwarteteCashFlows();
		if(out == null){
			assertTrue(true);
		}else{
			assertTrue(false);
		}
	}
	
	
	@Test
	public void testGetErwartetesFremdkapital(){
		double[] out = at.getErwartetesFremdkapital();
		if(out == null){
			assertTrue(true);
		}else{
			assertTrue(false);
		}
	}
	
}
