package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvExport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvImport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.ExportListener;
import dhbw.ka.mwi.businesshorizon2.demo.saving.ImportListener;
import dhbw.ka.mwi.businesshorizon2.demo.ui.controls.JPercentField;

import javax.swing.*;
import java.awt.*;

public class SzenarioPanel extends JPanel {

    private final JPercentField ekKosten = new JPercentField();
    private final JPercentField perSteuer = new JPercentField();
    private final JPercentField jahresUeberschuss = new JPercentField();
    private final JPercentField strukturbilanzen = new JPercentField();
    private final JPercentField zinsaufwand = new JPercentField();
    private final JComboBox<CFAlgo> algo = new JComboBox<>(CFAlgo.values());

    SzenarioPanel() {
        final JPanel innerPanel = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        add(innerPanel,BorderLayout.NORTH);

        final JButton save = new JButton("Speichern");
        final JButton load = new JButton("Laden");
        final JPanel buttonPanel = new JPanel(new GridLayout(0,2));
        buttonPanel.add(save);
        buttonPanel.add(load);
        innerPanel.add(buttonPanel, BorderLayout.NORTH);
        final JPanel fields = new JPanel(new  GridLayout(0,2));
        fields.add(new JLabel(Texts.EK_KOSTEN.toString()));
        ekKosten.setValue(0.100582);
        fields.add(ekKosten);

        fields.add(new JLabel(Texts.PER_STEUER.toString()));
        perSteuer.setValue(0.35);
        fields.add(perSteuer);

        fields.add(new JLabel(Texts.JAHRES_UEBERSCHUSS.toString()));
        jahresUeberschuss.setValue(0.15);
        fields.add(jahresUeberschuss);

        fields.add(new JLabel(Texts.STRUKTURBILANZEN.toString()));
        strukturbilanzen.setValue(0.25);
        fields.add(strukturbilanzen);


        fields.add(new JLabel(Texts.ZINSAUFWAND.toString()));
        zinsaufwand.setValue(0.08);
        fields.add(zinsaufwand);

        fields.add(new JLabel("Algo"));
        fields.add(algo);
        innerPanel.add(fields);

        save.addActionListener(new ExportListener(file -> CsvExport.exportScenario(this, file)));
        load.addActionListener(new ImportListener(file -> CsvImport.importSzenario(this,file)));
    }

    public JPercentField getEkKosten() {
        return ekKosten;
    }

    public JPercentField getPerSteuer() {
        return perSteuer;
    }

    public JPercentField getJahresUeberschuss() {
        return jahresUeberschuss;
    }

    public JPercentField getStrukturbilanzen() {
        return strukturbilanzen;
    }

    public JPercentField getZinsaufwand() {
        return zinsaufwand;
    }

    public JComboBox<CFAlgo> getAlgo() {
        return algo;
    }

}
