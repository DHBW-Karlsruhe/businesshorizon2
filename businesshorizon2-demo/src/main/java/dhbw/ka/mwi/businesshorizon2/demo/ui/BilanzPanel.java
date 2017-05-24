package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.models.BilanzModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvExport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvImport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.ExportListener;
import dhbw.ka.mwi.businesshorizon2.demo.saving.ImportListener;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class BilanzPanel extends JPanel {
    private final JTable table;


    BilanzPanel(final HeaderPanel headerPanel) {
        setLayout(new BorderLayout());

        final JButton save = new JButton("Speichern");
        final JButton load = new JButton("Laden");
        final JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        buttonPanel.add(save);
        buttonPanel.add(load);
        add(buttonPanel, BorderLayout.NORTH);

        table = new JTable(BilanzModelProvider.getModel((Integer) headerPanel.getBasisjahr().getValue(),(Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode()));

        final JScrollPane scroller = new JScrollPane(table);
        add(scroller);

        save.addActionListener(new ExportListener(file -> CsvExport.exportBilanz(getModel(), file)));
        load.addActionListener(new ImportListener(file -> setModel(CsvImport.importBilanz(file,(Integer) headerPanel.getPerioden().getValue(),(Integer) headerPanel.getBasisjahr().getValue(),headerPanel.getCurrentMode()))));
    }

    public void setModel(final TableModel model){
        table.setModel(model);
    }

    public TableModel getModel(){
        return table.getModel();
    }
}
