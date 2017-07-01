package dhbw.ka.mwi.businesshorizon2.demo.converter;

import javax.swing.table.TableModel;

public final class ModelToArrayConverter {

    private ModelToArrayConverter() {
    }

    public static double[] getRow(final TableModel model, final int row) {
        return getRow(model, row, 1);
    }

    public static double[] getRow(final TableModel model, final int row, final int start) {
        final double[] data = new double[model.getColumnCount() - start];
        for (int col = 0; col < data.length; col++) {
            data[col] = (double) model.getValueAt(row, col + start);
        }
        return data;
    }
}
