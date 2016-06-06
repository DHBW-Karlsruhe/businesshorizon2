package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

/**
 * 
 * @author Jonathan
 *
 * Diese Klasse wird durch die Zeitreihenanalyse erstellt und weitergegeben. Sie beinhaltet
 * stellt die Verteilung nicht durch Mittelwert und Varianz, sondern anhand ihrer Werte
 * dar. 
 */
public class Distribution {
	//Werte der Verteilung mit den Dimensionen [perioden] [Werte klassiert nach Wertebereichen]
	double [][] values;
	//gibt an, bei welchem Wert der Wertebereich startet - mit Hilfe von intervalLength ist Wertebereich bekannt
	double [] intervalStartValues;
	double intervalLength;
	//minimaler Wert des gesamten Arrays values (alle Dimensionen)
	double minValue;
	//maximaler Wert des gesamten Arrays values (alle Dimensionen)
	double maxValue;
	
	public Distribution (int periods, int length) {
		values = new double [periods][length];
	}
	//Methode fügt Werte zu zwei-dimensionalem Array values hinzu
	//wird für jede Periode einmal aufgerufen und prüft, in welchen Wertebereich ein Wert gehört.
	public void addValues(int period, double [] values) {
		int temp=0;
		for (int i=0; i<values.length; i++) {
			if (values[i]>this.intervalStartValues[temp]+this.intervalLength) {
				temp++;
			}
			this.values[period][temp]++;
		}
	}
	public void setIntervalStartValues(double[] values) {
		this.intervalStartValues=values;
	}
	public double [] getIntervalStartValues() {
		return this.intervalStartValues;
	}
	public void setIntervalLength (double length) {
		this.intervalLength = length;
	}
	public double getIntervalLength () {
		return this.intervalLength;
	}
	public void setMinValue(double min) {
		this.minValue=min;
	}
	public double getMinValue() {
		return this.minValue;
	}
	public void setMaxValue(double max) {
		this.maxValue=max;
	}
	public double getMaxValue() {
		return this.maxValue;
	}
}
