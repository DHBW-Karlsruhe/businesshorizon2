package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HeaderPanel extends JPanel {
    private final JRadioButton deter = new JRadioButton("Deterministisch");
    private final JRadioButton stochi = new JRadioButton("Stochastisch");

    private final JSpinner basisjahr = new JSpinner();
    private final JSpinner perioden = new JSpinner();

    private final JButton save = new JButton("Speichern");
    private final JButton load = new JButton("Laden");

    HeaderPanel() {
        final JPanel innerPanel = new JPanel(new GridBagLayout());
        setLayout(new BorderLayout());
        add(innerPanel, BorderLayout.NORTH);

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        innerPanel.add(save, c);
        c.gridx = 1;
        c.gridy = 0;
        innerPanel.add(load, c);

        final ButtonGroup inputMethod = new ButtonGroup();
        inputMethod.add(deter);
        inputMethod.add(stochi);

        deter.setSelected(true);
        c.gridx = 0;
        c.gridy = 1;
        innerPanel.add(deter, c);
        c.gridx = 1;
        c.gridy = 1;
        innerPanel.add(stochi, c);

        c.gridx = 0;
        c.gridy = 2;
        innerPanel.add(new JLabel(Texts.BASISJAHR.toString()), c);
        basisjahr.setModel(new SpinnerNumberModel(new GregorianCalendar().get(Calendar.YEAR), 1900, 3000, 1));

        //Tausender-Punkt im Spinner entfernen
        basisjahr.setEditor(new JSpinner.NumberEditor(basisjahr, "#"));

        c.gridx = 1;
        c.gridy = 2;
        innerPanel.add(basisjahr, c);

        c.gridx = 0;
        c.gridy = 3;
        innerPanel.add(new JLabel(Texts.PERIODEN.toString()), c);
        perioden.setModel(new SpinnerNumberModel(3, 3, 10, 1));
        c.gridx = 1;
        c.gridy = 3;
        innerPanel.add(perioden, c);
    }

    public JSpinner getPerioden() {
        return perioden;
    }

    public JRadioButton getDeter() {
        return deter;
    }

    public JRadioButton getStochi() {
        return stochi;
    }

    public CFMode getCurrentMode() {
        return deter.isSelected() ? CFMode.DETER : CFMode.STOCHI;
    }

    public JSpinner getBasisjahr() {
        return basisjahr;
    }

    JButton getSave() {
        return save;
    }

    JButton getLoad() {
        return load;
    }
}
