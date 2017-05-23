package dhbw.ka.mwi.businesshorizon2.demo.converter;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.GUV;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public final class ModelToGuvConverter {

    private ModelToGuvConverter() {
    }

    public static List<GUV> convert(final TableModel model){
        final List<GUV> guvs = new ArrayList<>();
        guvs.add(null);
        for (int i = 2; i < model.getColumnCount(); i++) {
            guvs.add(new GUV((Double) model.getValueAt(0, i), (Double) model.getValueAt(1, i), (Double) model.getValueAt(2, i)));
        }

        return guvs;
    }
}
