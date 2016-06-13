package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import junit.framework.TestCase;
/**
 * Diese Klasse testet die Implementierung der White Noise Berechnung
 * @author Philipp Nagel
 *
 */
public class TestWhiteNoise extends TestCase {
	@Test public void testWhiteNoiseMethod(){
		double standardabweichung = 5;
		double mittelwert = 5;
		double werteInnerhalbToleranz=0;
		double untereGrenze=mittelwert - standardabweichung;
		double obereGrenze=mittelwert + standardabweichung;
		for(int i=0;i<100000;i++){
			Double whiteNoise = AnalysisTimeseries.getWhiteNoiseValue(standardabweichung, mittelwert);
			if(whiteNoise>untereGrenze && whiteNoise<obereGrenze){
				werteInnerhalbToleranz++;
			}
			
		}
		boolean toleranzSchwelleNichtUeberschritten = true;
		double untereAnteilsToleranzGrenze = 0.65; //innerhalb von der standardabweichung sind  68,27% der der Werte 
		double obereAnteilsToleranzGrenze = 0.705;
		double anteilWerteInToleranz = werteInnerhalbToleranz / 100000.0;
		if(anteilWerteInToleranz<untereAnteilsToleranzGrenze || anteilWerteInToleranz>obereAnteilsToleranzGrenze){
			toleranzSchwelleNichtUeberschritten=false;
		}
		boolean isANumber = true;
		if(Double.isNaN(AnalysisTimeseries.getWhiteNoiseValue(standardabweichung, mittelwert))){
			isANumber=false;
		}
		assertTrue(toleranzSchwelleNichtUeberschritten);
		assertTrue(isANumber);
	}
	
}
