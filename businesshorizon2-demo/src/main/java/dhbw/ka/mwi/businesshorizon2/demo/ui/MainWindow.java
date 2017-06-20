package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.cf.*;
import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import dhbw.ka.mwi.businesshorizon2.demo.CFCalculator;
import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.ModelCopier;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvExport;
import dhbw.ka.mwi.businesshorizon2.demo.saving.CsvImport;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {

    private final CompanyPanel company;
    private final SzenarioPanel szenario;
    private final StochiResultPanel stochiResultPanel;
    private final DeterResultPanel deterResultPanel;
    private final HeaderPanel header;

    public MainWindow() {
        setTitle("Business Horizon Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        final JTabbedPane tab = new JTabbedPane();
        add(tab);

        header = new HeaderPanel();
        tab.addTab("Kopf",header);

        company = new CompanyPanel(header);
        tab.addTab("Unternehmenswerte", company);

        szenario = new SzenarioPanel();
        tab.addTab("Szenario",szenario);

        deterResultPanel = new DeterResultPanel();
        tab.addTab("Unternehmenswert", deterResultPanel);

        stochiResultPanel = new StochiResultPanel();

        final ChangeListener yearAndPeriodenListener = e -> {
            final TableModel oldCompany = company.getModel();
            final TableModel newCompany = CompanyModelProvider.getModel((Integer) header.getBasisjahr().getValue(), (Integer) header.getPerioden().getValue(), header.getCurrentMode());
            ModelCopier.copyModel(oldCompany, newCompany, header.getCurrentMode());
            company.setModel(newCompany);
        };

        header.getPerioden().addChangeListener(yearAndPeriodenListener);
        header.getBasisjahr().addChangeListener(yearAndPeriodenListener);

        header.getStochi().addActionListener(e -> {
            company.setModel(CompanyModelProvider.getModel((Integer) header.getBasisjahr().getValue(),(Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            switch (header.getCurrentMode()){
                case STOCHI:
                    tab.remove(deterResultPanel);
                    tab.addTab("Unternehmenswert", stochiResultPanel);
                    break;
                case DETER:
                    tab.remove(stochiResultPanel);
                    tab.addTab("Unternehmenswert", deterResultPanel);
                    break;
            }

        });

        header.getDeter().addActionListener(e -> {
            company.setModel(CompanyModelProvider.getModel((Integer) header.getBasisjahr().getValue(),(Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            switch (header.getCurrentMode()){
                case STOCHI:
                    tab.remove(deterResultPanel);
                    tab.addTab("Unternehmenswert", stochiResultPanel);
                    break;
                case DETER:
                    tab.remove(stochiResultPanel);
                    tab.addTab("Unternehmenswert", deterResultPanel);
                    break;
            }
        });

        header.getLoad().addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    CsvImport.importCSV(chooser.getSelectedFile(),header,company,szenario);
                } catch (final Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Datei kann nicht importiert werden: " + e1.getLocalizedMessage());
                }
            }
        });

        header.getSave().addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                final File file = chooser.getSelectedFile().getName().endsWith(".csv") ? chooser.getSelectedFile() : new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
                try {
                    CsvExport.export(header,szenario,company.getModel(),file);
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        stochiResultPanel.getCalculate().addActionListener(e -> {
            try {
                final CFCalculator calculator = new CFCalculator(company, szenario, (CFAlgo) stochiResultPanel.getAlgo().getSelectedItem());

                final long was = System.nanoTime();
                final double[] uWerts = calculator.calculateStochi();
                final double uWert = CFCalculator.avg(uWerts);
                stochiResultPanel.displayStochi(uWerts);
                System.out.println((System.nanoTime() - was) / 1000000);
                stochiResultPanel.getuWert().setText(String.valueOf(uWert));
                header.getStatus().setText("");
            } catch (final Exception e1) {
                e1.printStackTrace();
                header.getStatus().setText(e1.getLocalizedMessage());
            }
        });


        deterResultPanel.getCalculate().addActionListener(e -> {
            try {
                final CFCalculator calculator = new CFCalculator(company, szenario, (CFAlgo) stochiResultPanel.getAlgo().getSelectedItem());
                final CFParameter parameter = calculator.getParameter();
                switch ((CFAlgo) deterResultPanel.getAlgo().getSelectedItem()){
                    case APV:
                        final APVResult apvResult = new APV().calculateUWert(parameter);
                        deterResultPanel.displayAPV(apvResult,parameter.getFK()[0]);
                        deterResultPanel.getuWert().setText(String.valueOf(apvResult.getuWert()));
                        break;
                    case FCF:
                        final FCFResult fcfResult = new FCF().calculateUWert(parameter);
                        deterResultPanel.displayFCF(fcfResult,parameter.getFK()[0]);
                        deterResultPanel.getuWert().setText(String.valueOf(fcfResult.getuWert()));
                        break;
                    case FTE:
                        final CFResult fteResult = new FTE().calculateUWert(parameter);
                        deterResultPanel.displayFTE(fteResult);
                        deterResultPanel.getuWert().setText(String.valueOf(fteResult.getuWert()));
                        break;
                }

                final double uWert;
                header.getStatus().setText("");
            } catch (final Exception e1) {
                e1.printStackTrace();
                header.getStatus().setText(e1.getLocalizedMessage());
            }
        });
    }



}
