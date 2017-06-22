package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.FCFMode;
import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;

public class CompanyPanel extends JPanel {

    private final JTable table;
    private final JTable detailTable;

    CompanyPanel(final HeaderPanel headerPanel) {
        setLayout(new GridBagLayout());
        table = new JTable(CompanyModelProvider.getModel((Integer) headerPanel.getBasisjahr().getValue(),(Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode()));

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

        final JToggleButton detailButton = new JToggleButton("FCF aufdröseln");
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
        detailPanel.add(remove,c);
        final JButton add = new JButton("Zeile hinzufügen");
        c.gridx = 1;
        c.gridy = 1;
        detailPanel.add(add,c);

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

    public TableModel getModel(){
        return table.getModel();
    }

}
