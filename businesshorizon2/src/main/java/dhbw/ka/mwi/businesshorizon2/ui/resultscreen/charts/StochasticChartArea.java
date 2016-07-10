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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.charts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.Distribution;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.BasicColumnChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.BasicLineChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.ScenarioTable;

/**
 * Die StochasticChartArea komponiert die Ausgabe der Ergebnisse des
 * stochastischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier, Marcel Rosenberger, Markus Baader, Timo Rösch, Marius Müller
 * 
 */
public class StochasticChartArea extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("StochasticChartArea.class");
	private Label headline;

	private Label modulAbweichung;

	public Label getHeadline() {
		return headline;
	}

	public Label getModulAbweichung() {
		return modulAbweichung;
	}

	public StochasticChartArea(String methodName,
			TreeSet<? extends Period> periods,
//			CashFlowPeriodContainer periods,
			Distribution distribution, double validierung,
			Szenario scenario) {

		DecimalFormat df = new DecimalFormat("#0.00");

		// Überschrift anzeigen
		headline = new Label("<h2>Stochastic Calculation - " + methodName
				+ "<h2>");
		headline.setContentMode(Label.CONTENT_XHTML);
		headline.setHeight("50px");

		// Chart zur Anzeige der Unternehmenswerte
		List<String> cvChartColumns = new ArrayList<String>();
		cvChartColumns.add("Häufigkeit des Unternehmenswert");
		cvChartColumns.add("Erwartungswert");

		BasicColumnChart cvChart = new BasicColumnChart("Unternehmenswert",
				cvChartColumns);

		cvChart.addValues(distribution.getValues());
		cvChart.setHeight("300px");
		cvChart.setWidth("410px");
		cvChart.setStyleName("chart1");
		
		VerticalLayout vl = new VerticalLayout();
		Label hl = new Label("Verteilung der Unternehmenswerte");
		vl.addComponent(hl);
		vl.addComponent(cvChart);
		
		this.addComponent(vl);

	

		// Chart zur Anzeige des Cashflow Verlaufs
		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Erwartete Cashflows");
			cfChartLines.add("Erwartetes Fremdkapital");

			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart("Erwartete Werte",
					cfChartLines);
			//Werte hinzufügen und auf zwei Nachkommastellen runden
//			for (CashFlowPeriod period : periods) {
			for (Period period : periods) {
				cfChartValues
						.put(Integer.toString(period.getYear()),
								new double[] {
										Double.parseDouble((df.format(period
												.getFreeCashFlow())).replace(
												",", ".")),
										Double.parseDouble((df.format(period
												.getCapitalStock())).replace(
												",", ".")) });

			}

			cfChart.addValues(cfChartValues);
			cfChart.setHeight("200px");
			cfChart.setWidth("510px");
			cfChart.addStyleName("chart2");
			
			VerticalLayout vl2 = new VerticalLayout();
			Label hl2 = new Label("prognostizierte Free-Cashflows und Fremdkapital");
			vl2.addComponent(hl2);
			vl2.addComponent(cfChart);
			
			this.addComponent(vl2);
		}
		// Modellabweichung hinzufügen

		modulAbweichung = new Label("Die Modellabweichung beträgt "
				+ df.format(validierung) + "%");

		// Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("100px");
		st.setStyleName("chart3");
		
		VerticalLayout vl3 = new VerticalLayout();
		Label hl3 = new Label("Planungsprämissen");
		vl3.addComponent(hl3);
		vl3.addComponent(st);
		
		this.addComponent(vl3);

		// this.setHeight("900px");
		// this.setWidth("1024px");

	}
}
