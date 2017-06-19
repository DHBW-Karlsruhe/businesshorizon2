package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import dhbw.ka.mwi.businesshorizon2.demo.CFCalculator;
import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.ModelCopier;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

public class MainWindow extends JFrame {

    private final CompanyPanel company;
    private final SzenarioPanel szenario;
    private final ResultPanel resultPanel;
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

        resultPanel = new ResultPanel();
        tab.addTab("Unternehmenswert",resultPanel);

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
            resultPanel.getChartPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);
            resultPanel.getStochiPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);

        });

        header.getDeter().addActionListener(e -> {
            company.setModel(CompanyModelProvider.getModel((Integer) header.getBasisjahr().getValue(),(Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            resultPanel.getChartPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);
            resultPanel.getStochiPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);
        });

        resultPanel.getCalculate().addActionListener(e -> {
            try {
                final CFCalculator calculator = new CFCalculator(header, company, szenario, (CFAlgo)resultPanel.getAlgo().getSelectedItem());
                final double uWert;
                switch (header.getCurrentMode()){
                    case STOCHI:
                        final long was = System.nanoTime();
                        final double[] uWerts = calculator.calculateStochi();
                        uWert = CFCalculator.avg(uWerts);
                        resultPanel.displayStochi(uWerts);
                        System.out.println((System.nanoTime() - was) / 1000000);
                        break;
                    case DETER:
                        uWert = calculator.calculateDeter().getuWert();
                        break;
                    default:
                        uWert = 0;
                }
                resultPanel.getuWert().setText(String.valueOf(uWert));
                header.getStatus().setText("");
            } catch (final Exception e1) {
                e1.printStackTrace();
                header.getStatus().setText(e1.getLocalizedMessage());
            }
        });
    }



}
