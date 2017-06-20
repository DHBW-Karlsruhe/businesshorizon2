package dhbw.ka.mwi.businesshorizon2.demo;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemovedTimeSeries;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemover;
import dhbw.ka.mwi.businesshorizon2.cf.*;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.StochiResultPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;

import java.util.Arrays;

public final class CFCalculator {

    private CFCalculator() {
    }

    public static double[] calculateStochi(final CompanyPanel companyPanel, final SzenarioPanel szenarioPanel, final StochiResultPanel stochiResultPanel, final CFAlgo algo){
        final TrendRemovedTimeSeries fcfSeries = TrendRemover.removeTrend(ModelToArrayConverter.getRow(companyPanel.getModel(),0));
        final TrendRemovedTimeSeries fkSeries = TrendRemover.removeTrend(ModelToArrayConverter.getRow(companyPanel.getModel(),1));

        final ARModel fcfModel = AR.getModel(fcfSeries.getTimeSeriesWithoutTrend());
        final ARModel fkModel = AR.getModel(fkSeries.getTimeSeriesWithoutTrend());
        final CFAlgorithm cfAlgorithm = getAlgo(algo);
        return Stochi.doStochi((Integer) stochiResultPanel.getIter().getValue(), () -> {
            final double[] fcf = fcfSeries.getTimeSeriesWithTrend(fcfModel.predict((Integer) stochiResultPanel.getHorizont().getValue()));
            final double[] fk = fkSeries.getTimeSeriesWithTrend(fkModel.predict((Integer) stochiResultPanel.getHorizont().getValue() - 1));
            final double[] fk2 = appendLastValueAgain(fk);
            return cfAlgorithm.calculateUWert(getParameter(fcf,fk2,szenarioPanel)).getuWert();
        });
    }

    private static double[] appendLastValueAgain(final double[] series){
        final double[] withLast = Arrays.copyOf(series,series.length + 1);
        withLast[withLast.length - 1] = withLast[withLast.length - 2];
        return withLast;
    }

    public static double avg(final double[] values){
        double sum = 0;
        for (final double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private static CFAlgorithm getAlgo(final CFAlgo algo){
        switch (algo){
            case APV:
                return new APV();
            case FCF:
                return new FCF();
            case FTE:
                return new FTE();
        }
        throw new IllegalArgumentException("This shouldn't happen");
    }

    public static CFParameter getParameter(final CompanyPanel companyPanel, final SzenarioPanel szenarioPanel){
        return getParameter(ModelToArrayConverter.getRow(companyPanel.getModel(),0),ModelToArrayConverter.getRow(companyPanel.getModel(),1),szenarioPanel);
    }


    private static CFParameter getParameter(final double[] fcf, final double[] fk, final SzenarioPanel szenarioPanel){
        return new CFParameter(fcf, fk, (Double) szenarioPanel.getEkKosten().getValue(), (Double) szenarioPanel.getuSteusatz().getValue(), (Double) szenarioPanel.getFkKosten().getValue());
    }
}
