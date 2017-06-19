package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HeaderPanel extends JPanel {
    private final JLabel status = new JLabel();
    private final JRadioButton deter = new JRadioButton("Deterministisch");
    private final JRadioButton stochi = new JRadioButton("Stochastisch");

    private final JSpinner basisjahr = new JSpinner();
    private final JSpinner perioden = new JSpinner();

    HeaderPanel() {
        final JPanel innerPanel = new JPanel(new GridBagLayout());
        setLayout(new BorderLayout());
        add(innerPanel,BorderLayout.NORTH);

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        final JButton save = new JButton("Speichern");
        final JButton load = new JButton("Laden");
        c.gridx = 0;
        c.gridy = 0;
        innerPanel.add(save, c);
        c.gridx = 1;
        c.gridy = 0;
        innerPanel.add(load, c);

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        //importer.portFile(chooser.getSelectedFile());
                    } catch (final Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Datei kann nicht importiert werden: " + e1.getLocalizedMessage());
                    }
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final File file = chooser.getSelectedFile().getName().endsWith(".csv") ? chooser.getSelectedFile() : new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
                    /*try {
                        exporter.portFile(file);
                    } catch (final IOException e1) {
                        e1.printStackTrace();
                    }*/
                }
            }
        });


        final ButtonGroup inputMethod = new ButtonGroup();
        inputMethod.add(deter);
        inputMethod.add(stochi);

        deter.setSelected(true);
        c.gridx = 0;
        c.gridy = 1;
        innerPanel.add(deter,c);
        c.gridx = 1;
        c.gridy = 1;
        innerPanel.add(stochi,c);

        c.gridx = 0;
        c.gridy = 2;
        innerPanel.add(new JLabel("Basisjahr"),c);
        basisjahr.setModel(new SpinnerNumberModel(new GregorianCalendar().get(Calendar.YEAR), 1900, 3000, 1));
        c.gridx = 1;
        c.gridy = 2;
        innerPanel.add(basisjahr,c);

        c.gridx = 0;
        c.gridy = 3;
        innerPanel.add(new JLabel("Perioden"),c);
        perioden.setModel(new SpinnerNumberModel(3, 3, 10, 1));
        c.gridx = 1;
        c.gridy = 3;
        innerPanel.add(perioden,c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        innerPanel.add(status,c);

    }

    JLabel getStatus() {
        return status;
    }

    JSpinner getPerioden() {
        return perioden;
    }

    JRadioButton getDeter() {
        return deter;
    }

    JRadioButton getStochi() {
        return stochi;
    }

    CFMode getCurrentMode(){
        return deter.isSelected() ? CFMode.DETER : CFMode.STOCHI;
    }

    JSpinner getBasisjahr() {
        return basisjahr;
    }
}
