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

package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;

import Jama.Matrix;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleMatrix2D;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
 * Diese Klasse stellt die Methoden zur Verfuegung, die benoetigt werden, um die
 * Zeitreihenanalyse durch zu fuehren. Sie baut auf der YuleWalkerGleichung auf
 * und implementiert das AR-Modell.
 * 
 * @author Kai Westerholz, Nina Brauch, Raffaele Cipolla, Mirko Göpfrich, Marcel
 *         Rosenberger, Jonathan Janke
 * 
 */

public class AnalysisTimeseries {

	private static final Logger LOGGER = Logger.getLogger("AnalysisTimeseries.class");
	private double[] bereinigteZeitreihe;
	private double[] modellparameter;
	private double standardabweichung;
	private double mittelwert;
	private double[] erwarteteCashFlows;
	private double[] erwartetesFremdkapital;
	private double abweichung;
	private double[] autocorrelation;

	/**
	 * Methode zur Berechnung des Mittelwerts einer Zeitreihe.
	 * 
	 * @author Marcel Rosenberger, Nina Brauch, Mirko Göpfrich
	 * 
	 * @param zeitreihe
	 *            Durch den Benutzer gegebene Zeitreihe vergangener Werte.
	 * @return Gibt den Mittelwert einer Zeitreihe zurück.
	 * 
	 * 
	 * 
	 *         Speichern wir die Werte ebenfalls in ein Array ? Falls ja kann
	 *         die Methode übernommen werden
	 */

	public double berechneMittelwert(double[] zeitreihe) {
		double mittelwert = 0;
		double summe = 0;
		int zaehler = 0;

		for (int i = 0; i < zeitreihe.length; i++) {
			summe = summe + zeitreihe[i];
			zaehler = zaehler + 1;
		}

		if (zaehler != 0) {
			mittelwert = summe / zaehler;
		}

		return mittelwert;
	}

	/**
	 * Methode zur Berechnung der Varianz *
	 * 
	 * Berechnung der Varianz über den zuvor errechneten Mittelwert
	 */

	public double berechneVarianz(double[] zeitreihe) {
		double mittelwert = berechneMittelwert(zeitreihe);
		double varianz = 0;
		double s2 = 0;

		for (int i = 0; i < zeitreihe.length; i++) {
			s2 = zeitreihe[i] - mittelwert;
			s2 = s2 * s2;
			varianz = varianz + s2;
		}

		varianz = varianz / zeitreihe.length;
		return varianz;
	}

	/**
	 * Methode zur Berechnung der Standardabweichung *
	 * 
	 * Berechnung der Standardabweichung über den zuvor errechneten Varianz
	 */

	public double berechneStandardabweichung(double[] zeitreihe) {

		double standardabweichung = 0;
		double varianz = berechneVarianz(zeitreihe);

		standardabweichung = Math.sqrt(varianz);

		return standardabweichung;
	}

	/**
	 * Berechnung der Autokovarianzen einer Zeitreihe für verschiedene
	 * lags(Verschiebung) lag geht von 0 bis p-1
	 * 
	 * @author Jonathan Janke, Peter Kaisinger, Thuy Anh Nguyen
	 * @param zeitreihe:
	 *            Arraylist der Messwerte für die Zeitreihe von Zeitpunkt 0 bis
	 *            zur Ordnung p
	 * 
	 * @return results: beinhaltet alle Autokovarianzen der Zeitreihe für jedes
	 *         lag
	 */
	public double[] calculateAutocorrelations(double[] zeitreihe) {

		double[] results = new double[zeitreihe.length];
		double [] tempzeitreihe = new double [zeitreihe.length];
		
		//invertieren der Zeitreihe
		for (int i=0; i<tempzeitreihe.length; i++) {
			tempzeitreihe[i] = zeitreihe[tempzeitreihe.length-i-1];
		}

		//aufruf der Autokorrelationskalkulation für Zeitreihe und lags und einfügen in Array
		for (int i = 0; i < zeitreihe.length; i++) {
			results[i] = this.calculateAutocorrelation(tempzeitreihe, i);
		}
		return results;
	}

	/**
	 * Berechnung der Autokokorrelationen einer Zeitreihe für verschiedene
	 * lags(Verschiebung) lag geht von 0 bis p-1
	 * 
	 * @author Jonathan Janke, Peter Kaisinger, Thuy Anh Nguyen
	 * @param zeitreihe:
	 *            Arraylist der Messwerte für die Zeitreihe von Zeitpunkt 0 bis
	 *            zur Ordnung p
	 * 
	 * @return results: beinhaltet alle Autokokorrelationen der Zeitreihe für
	 *         jedes lag
	 */
	public double[] calculateAutocovariances(double[] zeitreihe) {

		double[] results = new double[zeitreihe.length];

		//kalkulieren der Autokovarianzen
		for (int i = 0; i < zeitreihe.length; i++) {
			results[i] = this.calculateAutocovariance(zeitreihe, i);
		}
		return results;
	}

	/**
	 * Berechnung der Autokovarianzen einer Zeitreihe zum gegebenen lag
	 * 
	 * @author Jonathan Janke, Peter Kaisinger, Thuy Anh Nguyen
	 * @param zeitreihe:
	 *            Arraylist der Messwerte für die Zeitreihe von Zeitpunkt 0 bis
	 *            zur Ordnung p
	 * @param lag:
	 *            gibt die Verschiebung der Zeitreihen an
	 * @return autocovariance: autorvarianz der Zeitreihe zum gegebenen lag
	 */
	public double calculateAutocovariance(double[] timeseries, int lag) {

		// Länge der Zeitreihe auf -lag angepasst
		double[] timeseries_shortened = new double[(timeseries.length - lag)];

		//Kürzung der Zeitreihe um Länge des Lags
		for (int k = 0; k < (timeseries.length - lag); k++) {
			timeseries_shortened[k] = timeseries[k];
		}

		// Mittelwert der originalen verkürzten Zeitreihe ermitteln
		double expectedValue_timeseries = this.berechneMittelwert(timeseries_shortened);
		
		double autocovariance = 0;

		// duplizierte Zeitreihenarray um Parameter lag verschoben
		double[] localtimeseries = new double[(timeseries.length - lag)];

		for (int i = 0; i < localtimeseries.length; i++) {
			localtimeseries[i] = timeseries[i + lag];
		}

		// Mittelwert der lokalen Zeitreihe ermitteln
		double expectedValue_localtimeseries = this.berechneMittelwert(localtimeseries);

		// berechnet die Autokovarianzen der Zeitreihe in Abhängigkeit von j
		for (int j = 0; j < localtimeseries.length; j++) {
			autocovariance += (timeseries_shortened[j] - expectedValue_timeseries)
					* (localtimeseries[j] - expectedValue_localtimeseries);
		}

		autocovariance = autocovariance / localtimeseries.length;

		return autocovariance;
	}

	/**
	 * Methode zur Berechnung der Autokorrelation einer Zeitreihe; Ausgangswert
	 * ist die Berechnung der Autokovarianz
	 * 
	 * @author Jonathan Janke, Peter Kaisinger, Thuy Anh Nguyen
	 * @param zeitreihe:
	 *            Arraylist der Zeitreihe mit den einzelnen Messwerten
	 * @param lag:
	 *            gibt den Wert der Verschiebung der Zeitreihe an
	 * @return Autokorrelation: berechnet aus Autokovarianz/Varianz
	 */
	public double calculateAutocorrelation(double[] zeitreihe, int lag) {
		//Autokorrelation = Autkovarianz/Varianz, da Standardabweichung konstant
		if (this.berechneVarianz(zeitreihe) == calculateAutocovariance(zeitreihe, lag))
			return 1;
		return this.calculateAutocovariance(zeitreihe, lag) / this.berechneVarianz(zeitreihe);
	}

	
	/**
	 * Methode zur Prognose zukünftiger Werte einer gegebenen Zeitreihe und
	 * einem AR-Modell. Sie berechnet die jeweiligen Prognosewerte und gibt sie
	 * in einem zweidimensionalen Array zurück.
	 * 
	 * JJ: Nomenklatur sollte ausschließlich Englisch sein.
	 * 
	 * @author Philipp Nagel, Thuy Anh Nguyen, Jonathan Janke
	 * 
	 * @param trendbereinigtezeitreihe
	 *            , die bereits trendbereinigte Zeitreihe. Die vordersten Werte
	 *            sind die aktuellsten.
	 * @param matrixPhi
	 *            die ermittelte Matrix Phi. Die vordersten Werte sind die
	 *            aktuellsten.
	 * @param standardabweichung
	 *            die ermittelte Standardabweichung der Zeitreihe
	 * @param Ordnung
	 *            p die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @param zuberechnendeperioden
	 *            die Anzahl der zu prognostizierenden, zukünftigen Perioden
	 * @param iterationen
	 *            die Anzahl der Iterationen, die die Zeitreihenanalyse
	 *            durchlaufen soll
	 * @param mittelwert
	 *            der ermittelte Mittelwert der Zeitreihe
	 * @param isfremdkapital
	 * @param double
	 *            konstanteC Wert der Konstante c die in der AR Methode addiert
	 *            wird. Momentan wird dieser Konstante ein Demowert von 5 in
	 *            dieser Funktion zugewiesen.
	 * @return Alle prognostizierten Werte in einem Array.
	 */
	public double[][] prognoseBerechnen(double[] trendbereinigtezeitreihe, double[] valuesForPhi,
			double standardabweichung, int zuberechnendeperioden, int iterationen, int p, double mittelwert,
			boolean isfremdkapital) {
		
		// ToDo: Dies ist eine Demowert und muss noch korrigiert werden
		double[][] stochastischeErgebnisseDerCashFlows = new double[trendbereinigtezeitreihe.length
				+ zuberechnendeperioden][iterationen];
		// Deklariere double Array für ergCF je t (length=1+Ordnung p + zu progn. Jahre)
		int alreadyOccupiedPlaces = 0;
		for (int i = 0; i < trendbereinigtezeitreihe.length; i++) {
			// Befüllen der ersten Werte mit den gegebenen Werten

			stochastischeErgebnisseDerCashFlows[i][0] = trendbereinigtezeitreihe[i];
			alreadyOccupiedPlaces = i + 1;
		}

		// Beginn des AR Teils
		for (int h = 0; h < iterationen; h++) {// Durchführen der Durchläufe
			double[] cashFlowsJeT = new double[p + 1];
			// Deklariere double Arrayfür CF je t (length = 1 + Ordnung p). Dieses Array wird sich später verschieben.
			// Dies muss jedes mal neu gemacht werden, da ansosten die Werte des
			// vorherigen Durchlaufes verwendet werden
			if (trendbereinigtezeitreihe.length < cashFlowsJeT.length) {
				// Vermeiden dass eine Exception entsteht, da nicht genügend Werte zur Verfügung stehen
				throw new IllegalArgumentException(
						"Wählen Sie ein kleineres p oder stellen sie eine längere Zeitreihe zur Verfügung");
			}
			for (int l = 0; l < cashFlowsJeT.length; l++) { 
				// Initialisiere CF je t Array aus Input
				cashFlowsJeT[l] = trendbereinigtezeitreihe[l];
			}
			for (int m = 0; m < zuberechnendeperioden; m++) {
				// Anzahl der Perioden die in die Zukunft geschaut werden soll
				// Wert berechnen für eine Periode
				
				double value = 0;
				for (int n = 0; n < p; n++) { 
					// einzubeziehende perioden (p)
					value += valuesForPhi[n] * cashFlowsJeT[n];
				}
				
				value += AnalysisTimeseries.getWhiteNoiseValue(standardabweichung, mittelwert);
				stochastischeErgebnisseDerCashFlows[alreadyOccupiedPlaces + m][h] =value;
				
				// Vordersten Wert von cashFlowsJeT freimachen
				for (int n = cashFlowsJeT.length - 1; n > 0; n--) {
					cashFlowsJeT[n] = cashFlowsJeT[n - 1];
				}
				cashFlowsJeT[0] = value; 
				// wert am anfang des CFFloat Array setzen
			}
		}
		//kopieren des Arrays damit nur Prognosewerte zurückgegeben werden
		double[][] results = new double[stochastischeErgebnisseDerCashFlows.length
				- alreadyOccupiedPlaces][iterationen];
		double [] nullArray = new double [iterationen];
		for (int i=0; i<nullArray.length; i++) {
			nullArray[i] = 0.0;
		}

		//wenn Fremdkapitel, dann letzte Stelle 0 (letzter Prognosewert)
		//wenn Cashflow, dann erste Stelle 0
		if (isfremdkapital) {
			for (int i = 0; i < results.length; i++) {
				results[i] = stochastischeErgebnisseDerCashFlows[i + alreadyOccupiedPlaces];
			}
			results[results.length-1] = nullArray;
		}
		else {
			for (int i = 0; i < results.length; i++) {
				results[i] = stochastischeErgebnisseDerCashFlows[i + alreadyOccupiedPlaces];
			}
			results[0] = nullArray;
		}

		return results;
	}

	/**
	 * Methode zur Berechnung des White Noise Wertes (weißes Rauschen). Das
	 * Weiße rauschen bildet eine gaussche Normalverteilung ab, welche sich um
	 * den Mittelwert bildet und deren Verteilung durch die Standardabweichung
	 * bestimmt wird.
	 * 
	 * @author Philipp Nagel, Jonathan Janke
	 * @param standardabweichung
	 * @param mittelwert
	 * @return Weißes Rauschen : 1 Wert für White Noise Fehlerterm
	 */

	public static double getWhiteNoiseValue(double standardabweichung, double mittelwert) {
		double whiteNoise = 0;
		Random r = new Random();
		// Berechnung des WhiteNoise Stoerterms über Std.Abweichung --> je höher die Werte variieren, desto höher der Ausschlag
		whiteNoise = r.nextGaussian() * standardabweichung + mittelwert;
		return whiteNoise;
	}

	/**
	 * Methode zum Erstellen einer Verteilung. Diese Methode teilt die
	 * Prognosewerte in verschiedene Werteklassen (Wertebereiche) ein und ordnet
	 * die Prognosewerte in diese Werteklassen ein. So wird ein grobes Bild der
	 * Verteilung geschaffen.
	 * 
	 * @author Jonathan Janke
	 * 
	 * @param prognosis
	 *            aus der Methode prognoseWertBerechnen
	 * @param numberOfValueClasses
	 *            gibt an, wie viele Werteklassen erzeugt werden. Wenn die
	 *            Prognosewerte von 1 bis 100 gehen und 10 Werteklassen
	 *            existieren, dann würden immer 10 Werte in eine Klasse gefasst
	 *            werden.
	 * @return verteilung der Werte
	 */
	public Distribution createStochasticPrognosis(double[][] prognosis, int numberOfValueClasses,
			double[] interestBearingDebtCapital, Szenario scenario) {
		// Wert 2 zur Erzeugung von double value Paaren
		APV apvCalc = new APV();
		double[] apvPrognosis = new double[prognosis[0].length];
		double[] tempPrognosis = new double[prognosis.length];
		for (int i = 0; i < prognosis[0].length; i++) {
			// Speichern der Perioden, um periodische Werte auszulesen
			for (int j = 0; j < prognosis.length; j++) {
				tempPrognosis[j] = prognosis[j][i];
			}
			//aufruf der APV-Kalkulation
			apvPrognosis[i] = apvCalc.calculateValues(tempPrognosis, interestBearingDebtCapital, scenario);
		}

		Distribution distribution = new Distribution(numberOfValueClasses, apvPrognosis);
		return distribution;
	}

	/**
	 * @param timeseries
	 *            urspruengliche Zeitreihe
	 * @return Matrix ausgehend von den autokorrelationen der urspruenglichen
	 *         Zeitreihe
	 * @author Jonathan Janke, Anh Nguyen, Peter Kaisinger
	 */
	public Matrix createMatrix(double[] timeseries) {
		double[][] resultMatrix = new double[timeseries.length][timeseries.length];

		double[] autocorrelations = this.calculateAutocorrelations(timeseries);
		
		// map autocorrelations to matrix
		for (int i = 0; i < resultMatrix.length; i++) {
			for (int j = 0; j < resultMatrix.length; j++) {
				int index = Math.abs(i - j);
				resultMatrix[i][j] = autocorrelations[index];
			}
		}

		return new Matrix(resultMatrix);

	}

	/**
	 * Reduziert die aktuelle Zeitreihe um deren Trend.
	 * 
	 * @author Jonathan Janke
	 * @param zeitreihe
	 *            [] die differenziert wird
	 * 
	 * @return differenziertes Array
	 */

	public double[] reduceTide(double[] zeitreihe) {
		// Trendbereinigung der Zeitreihe wenn diese nicht stationaer ist
		Trendgerade trend = new Trendgerade(zeitreihe);

		this.bereinigteZeitreihe = new double[zeitreihe.length];

		for (int i = 0; i < zeitreihe.length; i++) {
			this.bereinigteZeitreihe[i] = zeitreihe[i] - trend.getValue(i);
		}

		return bereinigteZeitreihe;
	}

	/**
	 * Addiert den aktuellen Trend auf die Zeitreihe
	 * 
	 * @author Jonathan Janke
	 * @param zeitreihe
	 *            [] die differenziert wurde
	 * 
	 * @return differenziertes Array
	 */
	public double[][] addTide(double[] timeseries, double[][] values) {
		// Trendbereinigung wieder draufschlagen
		// Perioden durchlaufen
		Trendgerade trend = new Trendgerade(timeseries);
		double newtide;
		for (int i = 0; i < values.length; i++) {
			// den Trend pro Periode ermitteln
			 newtide = trend.getValue(i);

			// alle Iterationen durchlaufen
			for (int j = 0; j < values[i].length; j++) {
				// auf jeden Wert (Prognosewerte und die erwarteten Cashflows)
				// den Trend wieder aufaddieren
				values[i][j] += newtide;
			}
		}
			return values;
	}

	/**
	 * Methode für die Durchführung einer Zeitreihenanalyse und Prognostizierung
	 * zukünftiger Werte.
	 * 
	 * @author Marcel Rosenberger, Mirko Göpfrich, Nina Brauch, Raffaele
	 *         Cipolla, Maurizio di Nunzio, Jonathan Janke
	 * @param zeitreihe
	 *            auf deren Basis die Prognose erfolgt
	 * @param p
	 *            die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @param zuberechnendeperioden
	 *            die Anzahl der zu prognostizierenden, zukünftigen Perioden
	 * @param durchlaeufe
	 *            die Anzahl der Iterationen, die die Zeitreihenanalyse
	 *            durchlaufen soll
	 * @param callback
	 *            das Callback-Objekt, über das die Kommunikation über Threads
	 *            hinweg gesteuert wird
	 * @return Alle prognostizierten Werte in einem Array.
	 */

	public double[][] calculate(double[] zeitreihe, int p, int zuberechnendePerioden, int durchlaeufe,
			CallbackInterface callback, boolean isfremdkapital) throws InterruptedException, StochasticMethodException {

		// vorbereitende Initialisierung
		double[][] prognosewerte = new double[zuberechnendePerioden][durchlaeufe];

		this.bereinigteZeitreihe = this.reduceTide(zeitreihe);

		// Start der zur Prognose benoetigten Berechnungen
		this.mittelwert = berechneMittelwert(bereinigteZeitreihe);
		this.autocorrelation = calculateAutocorrelations(bereinigteZeitreihe);

		this.modellparameter = calculateModelParameters(createMatrix(this.bereinigteZeitreihe), this.autocorrelation);
		// add parameters
		this.standardabweichung = this.berechneStandardabweichung(bereinigteZeitreihe);

		// Start der Prognose
		prognosewerte = prognoseBerechnen(bereinigteZeitreihe, modellparameter, standardabweichung,
				zuberechnendePerioden, durchlaeufe, p, mittelwert, isfremdkapital);
		prognosewerte = this.addTide(zeitreihe, prognosewerte);
		return prognosewerte;
	}

	public Distribution calculateAsDistribution(double[] zeitreihe, double[] initialInterestBearingDebtCapital, int p,
			int zuberechnendePerioden, int durchlaeufe, Szenario scenario, CallbackInterface callback)
			throws InterruptedException, StochasticMethodException {

		double[][] cashFlowPrognosis = this.calculate(zeitreihe, p, zuberechnendePerioden, durchlaeufe, callback,
				false);
		double[][] interestBearingDebtCapitaPrognosis = this.calculate(initialInterestBearingDebtCapital, p,
				zuberechnendePerioden, durchlaeufe, callback, true);

		double[] interestBearingDebtCapital = new double[interestBearingDebtCapitaPrognosis.length];
		for (int i = 0; i < interestBearingDebtCapitaPrognosis.length; i++) {
			interestBearingDebtCapital[i] = 0;
			for (double iteration : interestBearingDebtCapitaPrognosis[i]) {
				interestBearingDebtCapital[i] += iteration;
			}
			interestBearingDebtCapital[i] /= interestBearingDebtCapitaPrognosis[i].length;
		}

		int numberIntervals = 20;

		return this.createStochasticPrognosis(cashFlowPrognosis, numberIntervals, interestBearingDebtCapital,
				scenario);
	}
	
	/**
	 * @param Matrix ausgehend von den autokorrelationen der urspruenglichen
	 *         Zeitreihe, Autokorrelationen
	 * @return model parameters for AR model
	 * @author Jonathan Janke, Anh Nguyen, Peter Kaisinger
	 */

	public double[] calculateModelParameters(Matrix matrix, double[] autocorrelations) {
		Matrix inverseMatrix = matrix.inverse();

		Matrix autocorrelationMatrix = new Matrix(autocorrelations, autocorrelations.length);

		Matrix parameterMatrix = inverseMatrix.times(autocorrelationMatrix);

		double[][] twoDimensionArray = parameterMatrix.getArray();

		double[] oneDimeinsionArray = new double[autocorrelations.length];

		for (int i = 0; i < twoDimensionArray.length; i++) {
			oneDimeinsionArray[i] = twoDimensionArray[i][0];
		}

		return oneDimeinsionArray;
	}

	public double[] getErwarteteCashFlows() {
		return this.erwarteteCashFlows;
	}

	/*
	 * Diese Methode validiert das berechnete AR-Modell. Dabei wird angenommen,
	 * dass der Wert zum Zeitpunkt 0 noch nicht realisiert ist und wird aus dem
	 * berechneten Modell prognostiziert. Danach wird der Prognosewert mit dem
	 * tatsächlich realiserten Wert verglichen und eine prozentuale Abweichung
	 * berechnet.
	 * 
	 * @author: Nina Brauch
	 * 
	 * TODO: trendgerade Validierung mit realistischem Wert anstatt
	 * "new double [1]"
	 */

	public void validierung(DoubleArrayList trendbereinigtezeitreihe, DoubleMatrix2D matrixPhi, int p) {

		double prognosewert = 0;
		double realisierungsWert = trendbereinigtezeitreihe.get(trendbereinigtezeitreihe.size() - 1);
		Trendgerade trend = new Trendgerade(new double[1]);
		realisierungsWert = realisierungsWert + trend.getValue(p);
		// Ein Durchlauf findet den Gewichtungsfaktor Phi und den dazu passenden
		// Vergangenheitswert.
		// Hier wird der Prognosewert für den Zeitpunkt 0 berechnet
		for (int t = 0; t < p; t++) {
			prognosewert = prognosewert
					+ (matrixPhi.get(t, 0) * trendbereinigtezeitreihe.get(trendbereinigtezeitreihe.size() - (t + 2)));
		}
		prognosewert = prognosewert + trend.getValue(p);
		// Berechnung der prozentualen Abweichung
		double h = prognosewert / (realisierungsWert / 100);
		// Die Variable abweichung enthält die Abweichung in %, abweichung =1
		// --> Die Abweichung beträgt 1%
		double abweichung = Math.abs(h - 100);
		setAbweichung(abweichung);
	}

	public double getAbweichung() {
		return abweichung;
	}

	public void setAbweichung(double abweichung) {
		this.abweichung = abweichung;
	}

	public double[] getErwartetesFremdkapital() {
		return erwartetesFremdkapital;
	}

}
