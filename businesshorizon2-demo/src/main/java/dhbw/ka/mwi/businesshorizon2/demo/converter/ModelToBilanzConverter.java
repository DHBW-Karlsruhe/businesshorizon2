package dhbw.ka.mwi.businesshorizon2.demo.converter;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.Bilanz;

import javax.swing.table.TableModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ModelToBilanzConverter {

    private ModelToBilanzConverter() {
    }

    public static List<Bilanz> convert(final TableModel model){
        return IntStream.range(1, model.getColumnCount()).mapToObj(i -> new Bilanz((Double) model.getValueAt(0, i), (Double) model.getValueAt(1, i), (Double) model.getValueAt(2, i), (Double) model.getValueAt(3, i), (Double) model.getValueAt(4, i))).collect(Collectors.toList());
    }
}
