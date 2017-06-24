package dhbw.ka.mwi.businesshorizon2.demo.calc;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemovedTimeSeries;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemover;
import dhbw.ka.mwi.businesshorizon2.cf.*;
import dhbw.ka.mwi.businesshorizon2.demo.CFAlgo;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.StochiResultPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;

import java.util.Arrays;
import java.util.function.Supplier;

public final class CFCalculator {

    private CFCalculator() {
    }

    public static double[] calculateStochi(final CompanyPanel companyPanel, final SzenarioPanel szenarioPanel, final StochiResultPanel stochiResultPanel, final CFAlgo algo){
        final TrendRemovedTimeSeries fkSeries = TrendRemover.removeTrend(ModelToArrayConverter.getRow(companyPanel.getModel(),1));
        final int numPeriods = (Integer) stochiResultPanel.getHorizont().getValue();

        final Supplier<double[]> fcfSupplier = companyPanel.getDetailMode().get() ? new DetailFCFSupplier(companyPanel, numPeriods) : new SimpleFCFSupplier(companyPanel, numPeriods);

        final ARModel fkModel = AR.getModel(fkSeries.getTimeSeriesWithoutTrend());
        final CFAlgorithm cfAlgorithm = getAlgo(algo);
        return Stochi.doStochi((Integer) stochiResultPanel.getIter().getValue(), () -> {
            final double[] fcf = fcfSupplier.get();
            final double[] fk = fkSeries.getTimeSeriesWithTrend(fkModel.predict(numPeriods - 1));
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
