package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Ignore;
import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.cf.AssertRelative.assertRelative;

public class TestFCF {

    @Test
    public void testFCFBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(1055.24,result.getuWert());
        assertRelative(2315.24,result.getGk());
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(1569.18934438987,result.getuWert());
        assertRelative(2829.18934438987,result.getGk());
    }

    //Randbereiche und Sonderfälle, Excel-Pohl *********************************************************************************************

    //0€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 0 (Zu jeder Periode)
    @Test
    @Ignore("NaN bei FCF")
    public void nullEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,0,0,0,0},new double[]{0,0,0,0,0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(0.0, result.getuWert());
        assertRelative(0.0, result.getGk());
    }

    //1€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 1 (Zu jeder Periode)
    @Test
    public void testEinEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,1.0,1.0,1.0,1.0},new double[]{1.0,1.0,1.0,1.0,1.0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(9.2483867640, result.getuWert());
        assertRelative(10.2483867640, result.getGk());
    }

    //Milliardenwerte-Test
    @Test
    public void milliardenEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1450000000.0, 1260000000.0, 3340000000.0, 1680000000.0},new double[]{1360000000.0, 1600000000.0, 2200000000.0, 1500000000.0, 1500000000.0},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(16504187818.2333000000, result.getuWert());
        assertRelative(17864187818.23, result.getGk());
    }

    //Negative Werte-Test
    @Test
    public void negativeEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 176.76, -520.13, 404.87, -203.78},new double[]{-1260, -1300, 1000, -1400, -1400},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(-601.6495353431,result.getuWert());
        assertRelative(-1861.6495353431,result.getGk());
    }

    //Kleine, gemischte Werte-Test
    @Test
    public void kleineGemischteEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1.12, 0.78, 0.56, 1.01},new double[]{0.34, 1.2, 0.8, 0.51, 0.51},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(9.4465325137, result.getuWert());
        assertRelative(9.7865325137, result.getGk());
    }

    //Andere Steuersätze
    @Test
    public void andereSteuersaetzeTest() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.080722,0.349,0.06);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(2146.60456861283,result.getuWert());
        assertRelative(3406.60456861283,result.getGk());
    }
    
    //Andere Perioden
    @Test
    public void dreiPeriodenTest() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,404.8692500000,203.7832500000},new double[]{1300,1400,1400},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(1335.2312081890,result.getuWert());
        assertRelative(2635.2312081890,result.getGk());
    }
    
    //Viele Perioden Test
    @Test
    public void vielePeriodenTest() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,3960,4158,4365,4584,4813,5054,4587,5035,4035},new double[]{1625,1771,1931,2104,2294,2500,1850,2300,2468,2468},0.100582,0.30625,0.08);
        final FCFResult result = new FCF().calculateUWert(parameter);
        assertRelative(41640.17149257649,result.getuWert());
        assertRelative(43265.17149257649,result.getGk());
    }
}
