package dhbw.ka.mwi.businesshorizon2.cfsimple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFCF {

    @Test
    public void testFCFBallwieser() throws Exception {
        final CFEntityParameter parameter = new CFEntityParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);

        CFIntermediateResult result = new CFIntermediateResult(new double[]{1055.24,1053.75,1043.08,1000,1000},new double[]{2315.24,2375.75,2373.08,2400,2400},new double[]{0,0.1164,0.1175,0.1179,0.12});
        for (int i = 0; i < 100; i++) {
            result = new FCF().step(parameter,result);
        }
        assertEquals(1055.24,result.getuWert()[0],0.5);

        //assertEquals(1055.24,new FCF().calculateUWert(parameter),0.5);
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFEntityParameter parameter = new CFEntityParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.3063,0.08);
        assertEquals(1569.19,new FCF().calculateUWert(parameter),0.5);
    }
}
