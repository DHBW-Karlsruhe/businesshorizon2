package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFCalculator;
import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.models.BilanzModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.GuvModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.ModelCopier;

import javax.swing.*;
import javax.swing.table.TableModel;

public class MainWindow extends JFrame {

    private final GuvPanel guv;
    private final BilanzPanel bilanz;
    private final SzenarioPanel szenario;
    private final ResultPanel resultPanel;
    private final HeaderPanel header;

    public MainWindow() {
        setTitle("Business Horizon Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        header = new HeaderPanel();
        add(header);

        guv = new GuvPanel();
        add(guv);

        bilanz = new BilanzPanel();
        add(bilanz);

        szenario = new SzenarioPanel();
        add(szenario);

        resultPanel = new ResultPanel();
        add(resultPanel);

        header.getPerioden().addChangeListener(e -> {
            final TableModel oldBilanz = bilanz.getModel();
            final TableModel newBilanz = BilanzModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode());
            ModelCopier.copyModel(oldBilanz,newBilanz,header.getCurrentMode());
            bilanz.setModel(newBilanz);
            final TableModel oldGuv = guv.getModel();
            final TableModel newGuv = GuvModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode());
            ModelCopier.copyModel(oldGuv,newGuv,header.getCurrentMode());
            guv.setModel(newGuv);
        });

        header.getStochi().addActionListener(e -> {
            bilanz.setModel(BilanzModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            guv.setModel(GuvModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            resultPanel.getChartPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);
        });

        header.getDeter().addActionListener(e -> {
            bilanz.setModel(BilanzModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            guv.setModel(GuvModelProvider.getModel((Integer) header.getPerioden().getValue(), header.getCurrentMode()));
            resultPanel.getChartPanel().setVisible(header.getCurrentMode() == CFMode.STOCHI);
        });

        resultPanel.getCalculate().addActionListener(e -> {
            try {
                final CFCalculator calculator = new CFCalculator(header, bilanz, guv, szenario);
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
                        uWert = calculator.calculateDeter().getUnternehmenswertNow();
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
