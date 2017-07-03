package dhbw.ka.mwi.businesshorizon2.demo.calc;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;

import java.util.function.Supplier;

class SimpleFCFSupplier implements Supplier<double[]> {

    private final TimeSeries fcfSeries;
    private final ARModel fcfModel;
    private final int numPeriods;

    SimpleFCFSupplier(final CompanyPanel companyPanel, final int numPeriods, final int grad, final boolean trendy) {
        fcfSeries = trendy ? new TrendyTimeSeries(ModelToArrayConverter.getRow(companyPanel.getModel(), 0)) : new TimeSeries(ModelToArrayConverter.getRow(companyPanel.getModel(), 0));

        fcfModel = AR.getModel(fcfSeries.getValues(), grad);
        this.numPeriods = numPeriods;
    }

    @Override
    public double[] get() {
        return fcfSeries.applyModifications(fcfModel.predict(numPeriods));
    }
}
