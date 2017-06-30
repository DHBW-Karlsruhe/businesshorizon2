package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFCF {

    @Test
    public void testFCFBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(1055.24,result.getuWert(),1055.24  / 10000);
        assertEquals(2315.24,result.getGk(),2315.24 / 10000);
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(1569.19,result.getuWert(),1569.19  / 10000);
        assertEquals(2829.19,result.getGk(),2829.19 / 10000);
    }
    
    //Randbereiche und Sonderfälle, Excel-Pohl *********************************************************************************************
    
    //0€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 0 (Zu jeder Periode)
    @Test
    public void nullEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,0,0,0,0},new double[]{0,0,0,0,0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
//        Fehler NaN
//        assertEquals(0.0, result.getuWert(), 0);
//        assertEquals(0.0, result.getGk(), 0);
    }
    
    //1€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 1 (Zu jeder Periode)
    @Test
    public void testEinEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,1.0,1.0,1.0,1.0},new double[]{1.0,1.0,1.0,1.0,1.0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(9.25, result.getuWert(), 9.25 / 1000);
        assertEquals(10.25, result.getGk(), 10.25 / 1000);
    }
    
    //Milliardenwerte-Test
    @Test
    public void milliardenEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1450000000.0, 1260000000.0, 3340000000.0, 1680000000.0},new double[]{1360000000.0, 1600000000.0, 2200000000.0, 1500000000.0, 1500000000.0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(16479876705.852, result.getuWert(), 16479876705.852 / 100);
        assertEquals(17839876705.85, result.getGk(), 17839876705.85 / 100);
    }
    
    //Negative Werte-Test
    @Test
    public void negativeEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 176.76, -520.13, 404.87, -203.78},new double[]{-1260, -1300, 1000, -1400, -1400},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(-601.67,result.getuWert(), 601.67 / 10000);
        assertEquals(-1861.67,result.getGk(), 1861.67 / 10000);
    }
    
    //Kleine, gemischte Werte-Test
    @Test
    public void kleineGemischteEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1.12, 0.78, 0.56, 1.01},new double[]{0.34, 1.2, 0.8, 0.51, 0.51},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertEquals(9.45, result.getuWert(), 9.45 / 100);
        assertEquals(9.79, result.getGk(), 9.79 / 100);
    }
}
