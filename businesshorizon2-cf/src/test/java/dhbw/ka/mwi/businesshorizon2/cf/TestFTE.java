package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFTE {
    @Test
    public void testFTEBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertEquals(1055.24,new FTE().calculateUWert(parameter).getuWert(),1055.24 / 10000);
    }

    @Test
    public void testFTEPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.30625,0.08);
        assertEquals(1569.19,new FTE().calculateUWert(parameter).getuWert(),1569.19  / 10000);
    }
    
    //Testverfahren Excel-Pohl
    
    //0€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 0 (Zu jeder Periode)
    @Test
    public void nullEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,0,0,0,0},new double[]{0,0,0,0,0},0.1006,0.3063,0.08);
    	assertEquals(0.0, new FTE().calculateUWert(parameter).getuWert(), 0);
    }
    
    //1€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 1 (Zu jeder Periode)
    @Test
    public void testEinEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,1.0,1.0,1.0,1.0},new double[]{1.0,1.0,1.0,1.0,1.0},0.1006,0.3063,0.08);
    	assertEquals(9.25, new FTE().calculateUWert(parameter).getuWert(), 0.01);
    }
}
