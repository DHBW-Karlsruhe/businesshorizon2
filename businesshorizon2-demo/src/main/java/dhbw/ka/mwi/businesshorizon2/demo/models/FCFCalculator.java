package dhbw.ka.mwi.businesshorizon2.demo.models;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.FCFMode;

import javax.swing.table.DefaultTableModel;

public final class FCFCalculator {

    private FCFCalculator() {
    }

    public static void calculateFCF(final DefaultTableModel tableModel, final DefaultTableModel detailModel, final CFMode mode){
        for (int col = mode == CFMode.DETER ? 2 : 1; col < tableModel.getColumnCount(); col++) {
            tableModel.setValueAt(calculateColumnSum(detailModel,col + (mode == CFMode.DETER ? 0 : 1)),0,col);
        }
    }

    private static double calculateColumnSum(final DefaultTableModel detailModel, final int column){
        double sum = 0;
        for (int row = 0; row < detailModel.getRowCount(); row++) {
            switch ((FCFMode)detailModel.getValueAt(row,1)){
                case EINNAHMEN:
                    sum += (Double) detailModel.getValueAt(row,column);
                    break;
                case AUSGABEN:
                    sum -= (Double) detailModel.getValueAt(row,column);
                    break;
            }
        }
        return sum;
    }

}
