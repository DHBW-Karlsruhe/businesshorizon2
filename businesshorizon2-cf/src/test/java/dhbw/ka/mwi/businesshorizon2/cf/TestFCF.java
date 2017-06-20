package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFCF {

    @Test
    public void testFCFBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertEquals(1055.24,new FCF().calculateUWert(parameter).getuWert(),1055.24  / 10000);
        assertEquals(2315.24,new APV().calculateUWert(parameter).getGk(),2315.24 / 10000);
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.3063,0.08);
        assertEquals(1569.19,new FCF().calculateUWert(parameter).getuWert(),1569.19  / 10000);
        assertEquals(2829.19,new APV().calculateUWert(parameter).getGk(),2829.19 / 10000);
    }
}
