package dhbw.ka.mwi.businesshorizon2.cfsimple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFTE {
    @Test
    public void testFTEBallwieser() throws Exception {
        final CFEquityParameter parameter = new CFEquityParameter(new double[]{0,124.34,134.51,166.02,120},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);

        assertEquals(1055.24,new FTE().calculateUWert(parameter),0.5);
    }

    @Test
    public void testFTEPohl() throws Exception {
        final CFEquityParameter parameter = new CFEquityParameter(new double[]{0,146.83,147.98,749.37,126.08},new double[]{1260,1300,1000,1400,1400},0.100582,0.3063,0.08);
        assertEquals(1569.19,new FTE().calculateUWert(parameter),0.5);
    }
}
