package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static dhbw.ka.mwi.businesshorizon2.cf.AssertRelative.assertRelative;

public class TestAPV {

    @Test
    public void testAPVYannik() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{3960000000d,4158000000d,4365900000d,4584195000d,4813404750d,5054074987.5d,5054074987.5d},new double[]{162542000000d,177170780000d,193116150200d,210496603718d,229441298053d,250091014877d,250091014877d},0.1,0.26325,0.01);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(-48779385984d,result.getuWert());
        assertRelative(65122626224d,result.getTaxShield());
        assertRelative(48639987791d,result.getUwFiktiv());
        assertRelative(113762614016d,result.getGk());
    }

    @Test
    public void testAPVBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(1055.24,result.getuWert());
        assertRelative(363.21,result.getTaxShield());
        assertRelative(1952.03,result.getUwFiktiv());
        assertRelative(2315.24,result.getGk());
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(1569.18934438987,result.getuWert());
        assertRelative(415.694038002337,result.getTaxShield());
        assertRelative(2413.49530638754,result.getUwFiktiv());
        assertRelative(2829.18934438987,result.getGk());
    }
    
    //Randbereiche und Sonderfälle, Excel-Pohl *********************************************************************************************
    
    //0€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 0 (Zu jeder Periode)
    @Test
    public void nullEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,0,0,0,0},new double[]{0,0,0,0,0},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(0.0, result.getuWert());
        assertRelative(0.0, result.getTaxShield());
        assertRelative(0.0, result.getUwFiktiv());
        assertRelative(0.0, result.getGk());
    }
    
    //1€-Test
    //Alle Casflows und Fremdkapitalwerte besitzen den Wert 1 (Zu jeder Periode)
    @Test
    public void testEinEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0,1.0,1.0,1.0,1.0},new double[]{1.0,1.0,1.0,1.0,1.0},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(9.248386764, result.getuWert());
        assertRelative(0.30625, result.getTaxShield());
        assertRelative(9.94213676403333, result.getUwFiktiv());
        assertRelative(10.2483867640333, result.getGk());
    }
    
    //Milliardenwerte-Test
    @Test
    public void milliardenEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1450000000.0, 1260000000.0, 3340000000.0, 1680000000.0},new double[]{1360000000.0, 1600000000.0, 2200000000.0, 1500000000.0, 1500000000.0},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(16504187818.2333000000, result.getuWert());
        assertRelative(471913777.1173090000, result.getTaxShield());
        assertRelative(17392274041.1160000000, result.getUwFiktiv());
        assertRelative(17864187818.233300000000, result.getGk());
    }
    
    //Negative Werte-Test
    @Test
    public void negativeEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 176.76, -520.13, 404.87, -203.78},new double[]{-1260, -1300, 1000, -1400, -1400},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(-601.6495353431,result.getuWert());
        assertRelative(-376.796258192349,result.getTaxShield());
        assertRelative(-1484.85327715077,result.getUwFiktiv());
        assertRelative(-1861.6495353431,result.getGk()); 
    }
    
    //Kleine, gemischte Werte-Test
    @Test
    public void kleineGemischteEuroWerte() throws Exception {
    	final CFParameter parameter = new CFParameter(new double[]{0, 1.12, 0.78, 0.56, 1.01},new double[]{0.34, 1.2, 0.8, 0.51, 0.51},0.100582,0.30625,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(9.4465325137, result.getuWert());
        assertRelative(0.172464509348168, result.getTaxShield());
        assertRelative(9.61406800431095, result.getUwFiktiv());
        assertRelative(9.7865325137, result.getGk());
    }
    
    //Differenzierte Steuersätze
    @Test
    public void differenzierteSteuersätzeTest() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.080722,0.349,0.06);
        final APVResult result = new APV().calculateUWert(parameter);
        assertRelative(2146.60456861283,result.getuWert());
        assertRelative(476.938035962573,result.getTaxShield());
        assertRelative(2929.66653265026,result.getUwFiktiv());
        assertRelative(3406.60456861283,result.getGk());
    }
}
