package dhbw.ka.mwi.businesshorizon2.demo.calc;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemovedTimeSeries;
import dhbw.ka.mwi.businesshorizon2.ar.trendy.TrendRemover;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;

import java.util.function.Supplier;

class SimpleFCFSupplier implements Supplier<double[]> {

    private final TrendRemovedTimeSeries fcfSeries;
    private final ARModel fcfModel;
    private final int numPeriods;

    SimpleFCFSupplier(final CompanyPanel companyPanel, final int numPeriods, final int grad) {
        fcfSeries = TrendRemover.removeTrend(ModelToArrayConverter.getRow(companyPanel.getModel(), 0));
        fcfModel = AR.getModel(fcfSeries.getTimeSeriesWithoutTrend(), grad);
        this.numPeriods = numPeriods;
    }

    @Override
    public double[] get() {
        return fcfSeries.getTimeSeriesWithTrend(fcfModel.predict(numPeriods));
    }
}
