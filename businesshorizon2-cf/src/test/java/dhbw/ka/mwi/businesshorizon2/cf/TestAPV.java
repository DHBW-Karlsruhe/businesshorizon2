package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestAPV {

    @Test
    public void testAPVYannik() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{3960000000d,4158000000d,4365900000d,4584195000d,4813404750d,5054074987.5d,5054074987.5d},new double[]{162542000000d,177170780000d,193116150200d,210496603718d,229441298053d,250091014877d,250091014877d},0.1,0.26325,0.01);
        final APVResult result = new APV().calculateUWert(parameter);
        assertEquals(-48779385984d,result.getuWert(),48779385984d / 10000);
        assertEquals(65122626224d,result.getTaxShield(),65122626224d / 10000);
        assertEquals(48639987791d,result.getUwFiktiv(),48639987791d / 10000);
        assertEquals(113762614016d,result.getGk(),113762614016d / 10000);
    }

    @Test
    public void testAPVBallwieser() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertEquals(1055.24,result.getuWert(),1055.24 / 10000);
        assertEquals(363.21,result.getTaxShield(),363.21 / 10000);
        assertEquals(1952.03,result.getUwFiktiv(),1952.03 / 10000);
        assertEquals(2315.24,result.getGk(),2315.24 / 10000);
    }

    @Test
    public void testFCFPohl() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,176.76,520.13,404.87,203.78},new double[]{1260,1300,1000,1400,1400},0.100582,0.3063,0.08);
        final APVResult result = new APV().calculateUWert(parameter);
        assertEquals(1569.19,result.getuWert(),1569.19 / 10000);
        assertEquals(415.69,result.getTaxShield(),415.69 / 1000);
        assertEquals(2413.50,result.getUwFiktiv(),2413.50 / 10000);
        assertEquals(2829.19,result.getGk(),2829.19 / 10000);
    }
}
