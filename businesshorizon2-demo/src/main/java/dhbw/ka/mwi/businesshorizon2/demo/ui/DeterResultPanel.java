package dhbw.ka.mwi.businesshorizon2.demo.ui;

import dhbw.ka.mwi.businesshorizon2.cf.APVResult;
import dhbw.ka.mwi.businesshorizon2.cf.CFResult;
import dhbw.ka.mwi.businesshorizon2.cf.FCFResult;
import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.data.DataUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;

import javax.swing.*;
import java.awt.*;

class DeterResultPanel extends JPanel {

    private final JLabel uWert = new JLabel("0",SwingConstants.CENTER);
    private final JButton calculate = new JButton("Berechnen");
    private final JFreeChart chart;
    private final JComboBox<CFAlgo> algo = new JComboBox<>(CFAlgo.values());

    DeterResultPanel() {
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

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        northPanel.add(calculate,c);

        c.gridx = 1;
        c.gridy = 1;
        northPanel.add(uWert,c);

        add(northPanel,BorderLayout.NORTH);
        chart = createChart();
        add(new ChartPanel(chart));
    }

    private JFreeChart createChart() {
        CategoryAxis domainAxis = new CategoryAxis("");
        NumberAxis rangeAxis = new NumberAxis("");
        IntervalBarRenderer renderer = new IntervalBarRenderer();
        renderer.setBaseToolTipGenerator(new IntervalCategoryToolTipGenerator());
        renderer.setBaseToolTipGenerator((dataset, row, column) -> {
            IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
            return icd.getRowKey(row).toString() + ": " + (icd.getEndValue(row, column).doubleValue() - icd.getStartValue(row, column).doubleValue());
        });

        CategoryPlot plot = new CategoryPlot(null, domainAxis, rangeAxis, renderer);
        JFreeChart chart = new JFreeChart("Unternehmenswert", plot);
        plot.setDomainGridlinesVisible(true);
        plot.setRangePannable(true);
        ChartUtilities.applyCurrentTheme(chart);
        return chart;

    }

    JLabel getuWert() {
        return uWert;
    }

    JButton getCalculate() {
        return calculate;
    }

    void displayAPV(final APVResult result, double fk){
        final double[][] starts = {{0}, {0},{result.getUwFiktiv()},{result.getuWert()},{0}};
        final double[][] ends = {{result.getGk()}, {result.getUwFiktiv()},{result.getUwFiktiv() + result.getTaxShield()},{result.getuWert() + fk},{result.getuWert()}};
        final CategoryDataset dataset = new DefaultIntervalCategoryDataset(new String[]{"Gesamtkapital","EK (eigenfinanziert)","Tax shield","Fremdkapital","Unternehmenswert"},new String[]{""}, DataUtilities.createNumberArray2D(starts), DataUtilities.createNumberArray2D(ends));
        chart.getCategoryPlot().setDataset(dataset);
    }

    void displayFTE(final CFResult result, double fk){
        final double[][] starts = {{0}};
        final double[][] ends = {{result.getuWert()}};
        final CategoryDataset dataset = new DefaultIntervalCategoryDataset(new String[]{"Unternehmenswert"},new String[]{""}, DataUtilities.createNumberArray2D(starts), DataUtilities.createNumberArray2D(ends));
        chart.getCategoryPlot().setDataset(dataset);
    }

    void displayFCF(final FCFResult result, double fk){
        final double[][] starts = {{0},{result.getuWert()},{0}};
        final double[][] ends = {{result.getGk()}, {result.getuWert() + fk},{result.getuWert()}};
        final DefaultIntervalCategoryDataset dataset = new DefaultIntervalCategoryDataset(new String[]{"Gesamtkapital","Fremdkapital","Unternehmenswert"},new String[]{""}, DataUtilities.createNumberArray2D(starts), DataUtilities.createNumberArray2D(ends));
        chart.getCategoryPlot().setDataset(dataset);
    }

    public JComboBox<CFAlgo> getAlgo() {
        return algo;
    }
}
