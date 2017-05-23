package dhbw.ka.mwi.businesshorizon2.demo.models;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;

import javax.swing.table.TableModel;

public final class ModelCopier {

    private ModelCopier() {
    }

    public static void copyModel(final TableModel from, final TableModel to, final CFMode mode){
        for (int col = 0; col < from.getColumnCount() && col < to.getColumnCount(); col++) {
            switch (mode) {
                case STOCHI:
                    copyColumn(from,to,from.getColumnCount() - col - 1, to.getColumnCount() - col - 1);
                    break;
                case DETER:
                    copyColumn(from,to,col, col);
                    break;
            }
        }
    }

    private static void copyColumn(final TableModel from, final TableModel to, final int colFrom, final int colTo){
        if(colFrom == 0 || colTo == 0){
            return;
        }
        for (int row = 0; row < from.getRowCount(); row++) {
            to.setValueAt(from.getValueAt(row,colFrom),row,colTo);
        }
    }

}
