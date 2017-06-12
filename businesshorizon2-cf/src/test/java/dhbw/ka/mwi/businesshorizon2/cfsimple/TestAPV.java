package dhbw.ka.mwi.businesshorizon2.cfsimple;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestAPV {

    @Test
    public void testAPVYannik() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{3960000000d,4158000000d,4365900000d,4584195000d,4813404750d,5054074987.5d,5054074987.5d},new double[]{162542000000d,177170780000d,193116150200d,210496603718d,229441298053d,250091014877d,250091014877d},0.1,0.26325,0.01);
        assertEquals(-48779385984d,new APV().calculateUWert(parameter),1);
    }

    @Test
    public void testAPVBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertEquals(1055.24,new APV().calculateUWert(parameter),0.001);
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.35,0.08);
        assertEquals(1611.97,new APV().calculateUWert(parameter),0.001);
    }
}
