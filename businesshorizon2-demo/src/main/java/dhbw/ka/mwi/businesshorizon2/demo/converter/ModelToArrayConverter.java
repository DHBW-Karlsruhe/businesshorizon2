package dhbw.ka.mwi.businesshorizon2.demo.converter;

import javax.swing.table.TableModel;

public final class ModelToArrayConverter {

    private ModelToArrayConverter() {
    }

    public static double[] getRow(final TableModel model, final int row){
        final double[] data = new double[model.getColumnCount() - 1];
        for (int col = 0; col < data.length; col++) {
            data[col] = (double) model.getValueAt(row,col + 1);
        }
        return data;
    }
}
