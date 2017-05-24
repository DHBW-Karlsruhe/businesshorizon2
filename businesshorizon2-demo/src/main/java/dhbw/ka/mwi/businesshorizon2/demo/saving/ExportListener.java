package dhbw.ka.mwi.businesshorizon2.demo.saving;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ExportListener implements ActionListener {

    private final ImExporter exporter;

    public ExportListener(final ImExporter exporter) {
        this.exporter = exporter;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            final File file = chooser.getSelectedFile().getName().endsWith(".csv") ? chooser.getSelectedFile() : new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
            try {
                exporter.portFile(file);
            } catch (final IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
