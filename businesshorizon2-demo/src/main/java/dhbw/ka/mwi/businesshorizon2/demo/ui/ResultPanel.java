package dhbw.ka.mwi.businesshorizon2.demo.ui;

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

    ResultPanel() {
        setLayout(new BorderLayout());

        final JPanel northPanel = new JPanel(new GridLayout(0,2));

        uWert.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        northPanel.add(calculate);
        northPanel.add(uWert);
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
}
