package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Iterator;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

/**
 * 
 * @author Timo Rösch, Marius Müller, Markus Baader
 *
 */
public class DistributionCalculator {

	
    public Distribution calculate(Project project,
            CallbackInterface callback) throws InterruptedException,
            ConsideredPeriodsOfPastException, VarianceNegativeException,
            StochasticMethodException {
    	
    	double[] cashflow;
		double[] fremdkapital;
		Szenario scenario = project.getIncludedScenarios().get(0);
		
		AbstractPeriodContainer periodContainer = project.getStochasticPeriods();
		TreeSet<? extends Period> periods = periodContainer.getPeriods();
		Iterator<? extends Period> it = periods.iterator();
		Period period;
		int counter = 0;
		cashflow = new double[periods.size()];
		fremdkapital = new double[periods.size()];
		
		while (it.hasNext()) {
			period = it.next();
			cashflow[counter] = period.getFreeCashFlow();
			fremdkapital[counter] = period.getCapitalStock();
			counter++;
		}
		
    	AnalysisTimeseries analysisTimeseries = new AnalysisTimeseries();
    	Distribution distribution = analysisTimeseries.calculateAsDistribution(cashflow, fremdkapital, project.getRelevantPastPeriods(), project.getPeriodsToForecast(), project.getIterations(), scenario, callback);
    	
    	
    	return distribution;
    }
}
