package dhbw.ka.mwi.businesshorizon2.demo.models;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;

import javax.swing.table.DefaultTableModel;

public final class BilanzModelProvider {
    private BilanzModelProvider() {
    }

    public static DefaultTableModel getModel(final int basisjahr, final int perioden, final CFMode mode) {

        final DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return column != 0 && super.isCellEditable(row, column);
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return columnIndex > 0 ? Double.class : String.class;
            }
        };
        model.addColumn(Texts.HEADER);
        for (int i = 0; i < perioden; i++) {
            model.addColumn((mode == CFMode.DETER ? i : -perioden + i + 1) + basisjahr);
        }

        model.addRow(getRow(perioden,Texts.ANLAGE));
        model.addRow(getRow(perioden,Texts.UMLAUF));
        model.addRow(getRow(perioden,Texts.EK));
        model.addRow(getRow(perioden,Texts.ZINS_PF_PASSIVA));
        model.addRow(getRow(perioden,Texts.SONST_PASSIVA));

        return model;
    }

    private static Object[] getRow(final int perioden, final Texts text){
        final Object[] row = new Object[perioden + 1];
        row[0] = text;
        for (int i = 1; i < row.length; i++) {
            row[i] = 0d;
        }
        return row;
    }
}
