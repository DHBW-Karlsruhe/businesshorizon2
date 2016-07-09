/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.vaadinvisualizations.ColumnChart;
import org.vaadin.vaadinvisualizations.LineChart;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.WACC;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.DistributionCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.OneScenarioCalculationEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier, Annika Weis, Marcel Rosenberger, Maurizio di Nunzio,
 *         Timo Rösch, Marius Müller, Markus Baader
 * 
 */

public class OneScenarioResultPresenter extends
		Presenter<OneScenarioResultViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("OneScenarioResultPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private MethodRunner methodRunner;

	private Project project;

	private TreeSet<CashFlowPeriod> expectedValues;
	private double validierung;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Florian Stier
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@EventHandler
	public void onCalculateOneScenario(OneScenarioCalculationEvent event) {
		project = event.getProject();
		Szenario scenario = project.getIncludedScenarios().get(0);

		if (project.getProjectInputType().isDeterministic()) {
			double unternehmenswert = 0;
			double dFremdkapital = 0;
			double gesamtkapital;
			double steuervorteile = 0;
			double uwSteuerfrei = 0;

			double[] cashflow;
			double[] fremdkapital;

			AbstractPeriodContainer periodContainer = project
					.getDeterministicPeriods();
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

			if (periodContainer instanceof CashFlowPeriodContainer) {

			} else if (periodContainer instanceof UmsatzkostenVerfahrenCashflowPeriodContainer) {
				CashFlowCalculator
						.calculateUKVCashflows(
								(UmsatzkostenVerfahrenCashflowPeriodContainer) periodContainer,
								scenario);
			} else if (periodContainer instanceof GesamtkostenVerfahrenCashflowPeriodContainer) {
				CashFlowCalculator
						.calculateGKVCashflows(
								(GesamtkostenVerfahrenCashflowPeriodContainer) periodContainer,
								scenario);
			}

			AbstractDeterministicMethod method = project.getCalculationMethod();

			ColumnChart cc = new ColumnChart();
			cc.setOption("is3D", true);
			cc.setOption("isStacked", true);
			cc.setOption("legend", "bottom");
			cc.setOption("title", "Kapitalstruktur");
			cc.setOption("width", 250);
			cc.setOption("height", 240);
			cc.setColors(new String[] { "#92D050", "#D9D9D9", "#FF8533",
					"#0099FF" });
			cc.addXAxisLabel("Year");
			cc.addColumn("Eigenkapital");
			cc.addColumn("Fremdkapital");

			NumberFormat nfUS = NumberFormat.getInstance(Locale.US);
			nfUS.setMinimumFractionDigits(2);
			nfUS.setMaximumFractionDigits(2);
			project.setCompanyValue(Double.parseDouble(nfUS.format(
					unternehmenswert).replace(",", "")));
			NumberFormat nfDE = NumberFormat.getInstance(Locale.GERMANY);
			nfDE.setMaximumFractionDigits(2);
			nfDE.setMinimumFractionDigits(1);

			if (method.getName().equals("Flow-to-Equity (FTE)")) {
				FTE fte = new FTE();
				unternehmenswert = fte.calculateValues(cashflow, fremdkapital,
						scenario);
				dFremdkapital = fremdkapital[fremdkapital.length - 1];
				cc.add(String.valueOf(periods.last().getYear()),
						new double[] {
								Double.parseDouble(nfUS
										.format(unternehmenswert).replace(",",
												"")), dFremdkapital });
				logger.debug("Unternehmenswert mit FTE berechnet: "
						+ unternehmenswert);
			} else if (method.getName().equals("Adjusted-Present-Value (APV)")) {
				APV apv = new APV();
				unternehmenswert = apv.calculateValues(cashflow, fremdkapital,
						scenario);
				dFremdkapital = apv.getFremdkapital();
				steuervorteile = apv.getSteuervorteile();
				uwSteuerfrei = apv.getUwsteuerfrei();
				cc.addColumn("UW Steuerfrei");
				cc.addColumn("Steuervorteile");
				cc.add(String.valueOf(periods.last().getYear()),
						new double[] {
								Double.parseDouble(nfUS
										.format(unternehmenswert).replace(",",
												"")), dFremdkapital, 0, 0 });
				cc.add(String.valueOf(periods.last().getYear()),
						new double[] {
								0,
								0,
								Double.parseDouble(nfUS.format(uwSteuerfrei)
										.replace(",", "")),
								Double.parseDouble(nfUS.format(steuervorteile)
										.replace(",", "")) });
				logger.debug("Unternehmenswert mit APV berechnet: "
						+ unternehmenswert);
			} else if (method.getName().equals(
					"Weighted-Average-Cost-of-Capital (WACC)")) {
				WACC wacc = new WACC();
				unternehmenswert = wacc.calculateValues(cashflow, fremdkapital,
						scenario);
				dFremdkapital = wacc.getFremdkapital();
				cc.add(String.valueOf(periods.last().getYear()),
						new double[] {
								Double.parseDouble(nfUS
										.format(unternehmenswert).replace(",",
												"")), dFremdkapital });
				logger.debug("Unternehmenswert mit WACC berechnet: "
						+ unternehmenswert);
			}

			// getView().setCompanyValue(String.valueOf(unternehmenswert));
			// getView().setScenarioValue(String.valueOf(scenario.getRateReturnEquity()),
			// String.valueOf(scenario.getRateReturnCapitalStock()),
			// String.valueOf(scenario.getBusinessTax()),
			// String.valueOf(scenario.getCorporateAndSolitaryTax()));
			getView().setCompanyValue(nfDE.format(unternehmenswert));
			getView().setScenarioValue(
					nfDE.format(scenario.getRateReturnEquity()),
					nfDE.format(scenario.getRateReturnCapitalStock()),
					nfDE.format(scenario.getBusinessTax()),
					nfDE.format(scenario.getCorporateAndSolitaryTax()),
					nfDE.format(scenario.getPersonalTaxRate()));

			gesamtkapital = unternehmenswert + dFremdkapital;

			LineChart lc = new LineChart();
			lc.setOption("legend", "bottom");
			lc.setOption("title", "Verlauf der Cashflows");
			lc.setOption("width", 370);
			lc.setOption("height", 240);
			lc.setColors("#92D050");
			lc.addXAxisLabel("Year");
			lc.addLine("Cashflows");
			it = periods.iterator();
			while (it.hasNext()) {
				period = it.next();
				lc.add(String.valueOf(period.getYear()),
						new double[] { period.getFreeCashFlow() });
			}
			getView().setCapitalChart(cc);
			getView().setCashFlowChart(lc);
		} else if (project.getProjectInputType().isStochastic()) {
			AbstractPeriodContainer periodContainer = project
					.getStochasticPeriods();
			TreeSet<? extends Period> periods = periodContainer.getPeriods();

			
			DistributionCalculator distributionCalculator = new DistributionCalculator();
			Distribution distribution = null;
			try {
				distribution = distributionCalculator.calculate(project);
			} catch (Exception e) {
				logger.error("Error on calculating the distribution");
			}

			ColumnChart columnChart = new ColumnChart();
			columnChart.setOption("is3D", true);
			columnChart.setOption("isStacked", true);
			columnChart.setOption("legend", "bottom");
			columnChart.setOption("title", "Kapitalstruktur");
			columnChart.setOption("width", 250);
			columnChart.setOption("height", 240);
			columnChart.setColors(new String[] { "#92D050", "#D9D9D9",
					"#FF8533", "#0099FF" });
			columnChart.addXAxisLabel("Year");
			columnChart.addColumn("Unternehmenswert");
//			columnChart.addColumn("Fremdkapital");

			NumberFormat nfUS = NumberFormat.getInstance(Locale.US);
			nfUS.setMinimumFractionDigits(2);
			nfUS.setMaximumFractionDigits(2);
			// project.setCompanyValue(Double.parseDouble(nfUS.format(unternehmenswert).replace(",",
			// "")));
			NumberFormat nfDE = NumberFormat.getInstance(Locale.GERMANY);
			nfDE.setMaximumFractionDigits(2);
			nfDE.setMinimumFractionDigits(1);

//			String[] test = new String[3];
//			columnChart.addRow(new String[number]);
			
//			for(int i = 0; i < distribution.getValues().length; i++){
//				columnChart.addColumn("UW: " + (i + 1));
//			}
			int count = 1;
			for(double value : distribution.getValues()){
				columnChart.add(String.valueOf(count), new double[] {value});
				count++;
			}
//			columnChart.add(String.valueOf(periods.last().getYear()), distribution.getValues());
			
			
			
//			getView().setCompanyValue(nfDE.format(123));
			getView().setScenarioValue(
					nfDE.format(scenario.getRateReturnEquity()),
					nfDE.format(scenario.getRateReturnCapitalStock()),
					nfDE.format(scenario.getBusinessTax()),
					nfDE.format(scenario.getCorporateAndSolitaryTax()),
					nfDE.format(scenario.getPersonalTaxRate()));
			
			LineChart lineChart = new LineChart();
			lineChart.setOption("legend", "bottom");
			lineChart.setOption("title", "Verlauf der Cashflows");
			lineChart.setOption("width", 370);
			lineChart.setOption("height", 240);
			lineChart.setColors("#92D050");
			lineChart.addXAxisLabel("Year");
			lineChart.addLine("Cashflows");
			
			Iterator<? extends Period>  it = periods.iterator();
			Period period;
			while (it.hasNext()) {
				period = it.next();
				lineChart.add(String.valueOf(period.getYear()),
						new double[] { period.getFreeCashFlow() });
			}
			
			columnChart.setHeight("200px");
			columnChart.setWidth("510px");
			
			getView().setCapitalChart(columnChart);
			getView().setCashFlowChart(lineChart);
		}
	}

}
