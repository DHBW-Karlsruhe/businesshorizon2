package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class CompanyPanel extends JPanel {

    private final JTable table;

    CompanyPanel(final HeaderPanel headerPanel) {
        setLayout(new BorderLayout());
        table = new JTable(CompanyModelProvider.getModel((Integer) headerPanel.getBasisjahr().getValue(),(Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode()));

        final JScrollPane scroller = new JScrollPane(table);
        scroller.setMaximumSize(new Dimension(0,10));
        add(scroller);

    }

    public void setModel(final TableModel model){
        table.setModel(model);
    }

    public TableModel getModel(){
        return table.getModel();
    }

}
