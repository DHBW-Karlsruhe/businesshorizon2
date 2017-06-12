package dhbw.ka.mwi.businesshorizon2.cfsimple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFCF {

    @Test
    public void testFCF() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertEquals(1055.24,new FCF().calculateUWert(parameter),0.001);
    }

}
