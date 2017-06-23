package dhbw.ka.mwi.businesshorizon2.demo.models;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.FCFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;

import javax.swing.table.DefaultTableModel;

public final class CompanyModelProvider {
    private CompanyModelProvider() {
    }

    public static DefaultTableModel getModel(final int basisjahr, final int perioden, final CFMode mode) {

        final DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return (row > 0 || column > (mode == CFMode.DETER ? 1 : 0)) && super.isCellEditable(row, column);
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

        model.addRow(getRow(perioden, Texts.FCF,mode));
        model.addRow(getRow(perioden, Texts.FK, mode));

        return model;
    }

    private static Object[] getRow(final int perioden, final Texts text, final CFMode mode) {
        final Object[] row = new Object[perioden + 1];
        row[0] = text;
        for (int i = 1; i < row.length; i++) {
            if(i == 1 && mode == CFMode.DETER && text == Texts.FCF){
                row[1] = Double.NaN;
            } else {
                row[i] = 0d;
            }
        }
        return row;
    }


    public static DefaultTableModel getDetailModel(final int basisjahr, final int perioden, final CFMode mode) {

        final DefaultTableModel model = new DefaultTableModel() {

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                if(columnIndex == 0){
                    return String.class;
                }
                if (columnIndex == 1){
                    return FCFMode.class;
                }

                return Double.class;
            }
        };
        model.addColumn("Bezeichnung");
        model.addColumn("Typ");
        for (int i = mode == CFMode.DETER ? 1 : 0; i < perioden; i++) {
            model.addColumn((mode == CFMode.DETER ? i : -perioden + i + 1) + basisjahr);
        }

        model.addRow(getDetailRow(perioden, FCFMode.EINNAHMEN,mode));
        model.addRow(getDetailRow(perioden, FCFMode.AUSGABEN,mode));

        return model;
    }


    public static Object[] getDetailRow(final int perioden, final FCFMode fcfMode, final CFMode mode) {
        final Object[] row = new Object[perioden + (mode == CFMode.DETER ? 1 : 2)];
        row[0] = "Bezeichnung";
        row[1] = fcfMode;

        for (int i = 2; i < row.length; i++) {
            row[i] = 0d;
        }
        return row;
    }
}
