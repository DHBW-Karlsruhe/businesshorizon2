package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;

class ResultPanel extends JPanel {

    private final JLabel uWert = new JLabel("0",SwingConstants.CENTER);
    private final JButton calculate = new JButton("Berechnen");
    private final JFreeChart chart;
    private final ChartPanel chartPanel;
    private final JComboBox<CFAlgo> algo = new JComboBox<>(CFAlgo.values());
    private final JPanel stochiPanel = new JPanel(new GridLayout(0,2));
    private final JSpinner horizont = new JSpinner();
    private final JSpinner iter = new JSpinner();

    ResultPanel() {
        setLayout(new BorderLayout());

        final JPanel northPanel = new JPanel(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        uWert.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        c.gridx = 0;
        c.gridy = 0;
        northPanel.add(new JLabel("Algo"),c);

        c.gridx = 1;
        c.gridy = 0;
        northPanel.add(algo,c);

        stochiPanel.setVisible(false);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        northPanel.add(stochiPanel,c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        northPanel.add(calculate,c);

        c.gridx = 1;
        c.gridy = 2;
        northPanel.add(uWert,c);

        stochiPanel.add(new JLabel("PrognosePerioden"));
        horizont.setModel(new SpinnerNumberModel(3, 1, 10, 1));

        stochiPanel.add(horizont);

        stochiPanel.add(new JLabel("Unternehmenswerte"));
        iter.setModel(new SpinnerNumberModel(10000, 1, 100000, 1000));

        stochiPanel.add(iter);


        add(northPanel,BorderLayout.NORTH);
        chart = ChartFactory.createHistogram("Histogram","Euros","Wahrscheinlichkeit",null, PlotOrientation.VERTICAL,true,true,true);
        chartPanel = new ChartPanel(chart);
        chartPanel.setVisible(false);
        add(chartPanel);
    }

    JLabel getuWert() {
        return uWert;
    }

    JButton getCalculate() {
        return calculate;
    }

    void displayStochi(final double[] result){
        final HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Unternehmenswert",result,10);
        chart.getXYPlot().setDataset(dataset);
    }

    ChartPanel getChartPanel() {
        return chartPanel;
    }

    public JComboBox<CFAlgo> getAlgo() {
        return algo;
    }

    public JSpinner getIter() {
        return iter;
    }
    public JSpinner getHorizont() {
        return horizont;
    }

    public JPanel getStochiPanel() {
        return stochiPanel;
    }
}
