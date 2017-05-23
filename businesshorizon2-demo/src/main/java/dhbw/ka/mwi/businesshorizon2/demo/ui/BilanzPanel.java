package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.models.BilanzModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvExport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvImport;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BilanzPanel extends JPanel {
    private final JTable table;


    BilanzPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Struktur-Bilanzen"));

        final JButton save = new JButton("Speichern");
        final JButton load = new JButton("Laden");
        final JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        buttonPanel.add(save);
        buttonPanel.add(load);
        add(buttonPanel, BorderLayout.NORTH);

        table = new JTable(BilanzModelProvider.getModel(3, CFMode.DETER));

        final JScrollPane scroller = new JScrollPane(table);
        add(scroller);

        save.addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                final File file = chooser.getSelectedFile().getName().endsWith(".csv") ? chooser.getSelectedFile() : new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
                try {
                    CsvExport.exportBilanz(getModel(), file);
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        load.addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    setModel(CsvImport.importBilanz(chooser.getSelectedFile()));
                } catch (final Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Datei kann nicht importiert werden: " + e1.getLocalizedMessage());
                }
            }
        });
    }

    public void setModel(final TableModel model){
        table.setModel(model);
    }

    public TableModel getModel(){
        return table.getModel();
    }
}
