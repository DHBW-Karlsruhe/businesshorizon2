package dhbw.ka.mwi.businesshorizon2.cfsimple;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestAPV {

    @Test
    public void testAPV() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{3960000000d,4158000000d,4365900000d,4584195000d,4813404750d,5054074987.5d,5054074987.5d},new double[]{162542000000d,177170780000d,193116150200d,210496603718d,229441298053d,250091014877d,250091014877d},0.1,0.26325,0.01);
        assertEquals(-48779385984d,new APV().calculateUWert(parameter),1);
    }

}
