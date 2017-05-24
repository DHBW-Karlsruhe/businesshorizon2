package dhbw.ka.mwi.businesshorizon2.demo.saving;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportListener implements ActionListener {

    private final ImExporter importer;

    public ImportListener(final ImExporter importer) {
        this.importer = importer;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                importer.portFile(chooser.getSelectedFile());
            } catch (final Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Datei kann nicht importiert werden: " + e1.getLocalizedMessage());
            }
        }
    }
}
