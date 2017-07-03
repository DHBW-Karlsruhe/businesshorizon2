package dhbw.ka.mwi.businesshorizon2.ar.quality;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.ar.AssertRelative.assertRelative;

public class TestMSE {

    @Test
    public void testQuality() {
        final double[] data = {77327d, 76651d, 75978d, 73515d, 78296d, 75882d, 71920d, 75636d, 79644d};

        final ARModel model = AR.getModel(data);
        assertRelative(0.0324319187, new MSEQualityCalculator().calculateQuality(model));
    }

    @Test
    public void testQuality2() {
        final double[] data = {1260, 1320, 1330, 1400, 1400};

        final ARModel model = AR.getModel(data);
        assertRelative(0.04063198468733467, new MSEQualityCalculator().calculateQuality(model));
    }
}
