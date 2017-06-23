package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.FCFMode;
import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.FCFCalculator;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.function.Supplier;

public class CompanyPanel extends JPanel {

    private final JTable table;
    private final JTable detailTable;
    private final JToggleButton detailButton = new JToggleButton("FCF aufdröseln");
    private final Supplier<Boolean> detailMode = detailButton::isSelected;
    private final TableModelListener fcfRefresher;

    CompanyPanel(final HeaderPanel headerPanel) {
        setLayout(new GridBagLayout());
        table = new JTable(CompanyModelProvider.getModel((Integer) headerPanel.getBasisjahr().getValue(),(Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode(),detailMode));

        final JScrollPane scroller = new JScrollPane(table);
        scroller.setMaximumSize(new Dimension(0,10));

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(scroller,c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        add(detailButton,c);

        detailTable = new JTable(CompanyModelProvider.getDetailModel((Integer) headerPanel.getBasisjahr().getValue(),(Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode())){
            @Override
            public TableCellEditor getCellEditor(final int row, final int column) {
                if(column == 1){
                    return new DefaultCellEditor(new JComboBox<>(FCFMode.values()));
                }
                return super.getCellEditor(row, column);
            }
        };

        fcfRefresher = e -> FCFCalculator.calculateFCF(getModel(), getDetailModel(), headerPanel.getCurrentMode());

        detailTable.getModel().addTableModelListener(fcfRefresher);

        final JPanel detailPanel = new JPanel(new GridBagLayout());
        final JScrollPane detailScroller = new JScrollPane(detailTable);
        detailScroller.setMaximumSize(new Dimension(0,10));
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        detailPanel.setVisible(false);
        detailPanel.add(detailScroller,c);

        final JButton remove = new JButton("Zeile entfernen");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        remove.addActionListener(e -> {
            final int row = detailTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Erst Zeile markieren, dann löschen");
                return;
            }
            final DefaultTableModel tableModel = (DefaultTableModel) detailTable.getModel();
            tableModel.removeRow(detailTable.getSelectedRow());
        });
        detailPanel.add(remove,c);
        final JButton add = new JButton("Zeile hinzufügen");
        c.gridx = 1;
        c.gridy = 1;
        detailPanel.add(add,c);
        add.addActionListener(e -> {
            final DefaultTableModel tableModel = (DefaultTableModel) detailTable.getModel();
            tableModel.addRow(CompanyModelProvider.getDetailRow((Integer) headerPanel.getPerioden().getValue(),FCFMode.AUSGABEN,headerPanel.getCurrentMode()));
        });

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(detailPanel,c);

        detailButton.addChangeListener(e -> detailPanel.setVisible(detailButton.isSelected()));
    }

    public void setModel(final TableModel model){
        table.setModel(model);
    }

    public DefaultTableModel getModel(){
        return (DefaultTableModel) table.getModel();
    }

    public void setDetailModel(final TableModel model){
        detailTable.setModel(model);
        model.addTableModelListener(fcfRefresher);
    }

    public DefaultTableModel getDetailModel(){
        return (DefaultTableModel) detailTable.getModel();
    }

    public Supplier<Boolean> getDetailMode() {
        return detailMode;
    }
}
