package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.ui.controls.JPercentField;

import javax.swing.*;
import java.awt.*;

public class SzenarioPanel extends JPanel {

    private final JPercentField ekKosten = new JPercentField();
    private final JPercentField fkKosten = new JPercentField();
    private final JPercentField uSteusatz = new JPercentField();

    SzenarioPanel() {
        final JPanel innerPanel = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        add(innerPanel, BorderLayout.NORTH);

        final JPanel fields = new JPanel(new GridLayout(0, 2));
        fields.add(new JLabel(Texts.EK_KOSTEN.toString()));
        ekKosten.setValue(0.09969137);
        fields.add(ekKosten);

        fields.add(new JLabel(Texts.FK_KOSTEN.toString()));
        fkKosten.setValue(0.08);
        fields.add(fkKosten);

        fields.add(new JLabel(Texts.STEUSATZ.toString()));
        uSteusatz.setValue(0.26325);
        fields.add(uSteusatz);

        innerPanel.add(fields);
    }

    public JPercentField getEkKosten() {
        return ekKosten;
    }

    public JPercentField getFkKosten() {
        return fkKosten;
    }

    public JPercentField getuSteusatz() {
        return uSteusatz;
    }
}
