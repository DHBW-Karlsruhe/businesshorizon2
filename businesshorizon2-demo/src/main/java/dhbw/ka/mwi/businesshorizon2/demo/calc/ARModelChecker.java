package dhbw.ka.mwi.businesshorizon2.demo.calc;

import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.quality.MSEQualityCalculator;

import javax.swing.*;

final class ARModelChecker {

    private static final double MINQUALITY = 0.05;

    private ARModelChecker() {
    }

    static void checkARModel(final ARModel model){
        final double quality = new MSEQualityCalculator().calculateQuality(model);
        if(quality >= MINQUALITY){
            JOptionPane.showMessageDialog(null,"Das AR-Model ist qualitativ minderwertig: \n Qualität: " + quality);
        }

    }
}
