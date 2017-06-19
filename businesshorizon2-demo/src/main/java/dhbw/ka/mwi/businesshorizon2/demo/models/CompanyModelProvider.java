package dhbw.ka.mwi.businesshorizon2.demo.models;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;

import javax.swing.table.DefaultTableModel;

public final class CompanyModelProvider {
    private CompanyModelProvider() {
    }

    public static DefaultTableModel getModel(final int basisjahr, final int perioden, final CFMode mode) {

        final DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return column > (mode == CFMode.DETER ? 1 : 0) && super.isCellEditable(row, column);
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return columnIndex > (mode == CFMode.DETER ? 1 : 0) ? Double.class : String.class;
            }
        };
        model.addColumn(Texts.HEADER);
        for (int i = 0; i < perioden; i++) {
            model.addColumn((mode == CFMode.DETER ? i : -perioden + i + 1) + basisjahr);
        }

        model.addRow(getRow(perioden, Texts.FCF,mode));
        model.addRow(getRow(perioden, Texts.FK,mode));

        return model;
    }

    private static Object[] getRow(final int perioden, final Texts text, final CFMode mode) {
        final Object[] row = new Object[perioden + 1];
        row[0] = text;
        if(mode == CFMode.DETER) {
            row[1] = "-";
        }
        for (int i = mode == CFMode.DETER ? 2 : 1; i < row.length; i++) {
            row[i] = 0d;
        }
        return row;
    }
}
