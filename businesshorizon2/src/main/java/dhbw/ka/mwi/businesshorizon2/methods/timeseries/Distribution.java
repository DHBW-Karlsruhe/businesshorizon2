package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * 
 * @author Jonathan Janke
 *
 * Diese Klasse wird durch die Zeitreihenanalyse erstellt und weitergegeben. Sie beinhaltet
 * stellt die Verteilung nicht durch Mittelwert und Varianz, sondern anhand ihrer Werte
 * dar.
 * 
 * Der Konstruktur erwartet:
 * - die Prognose einer Periode mit n Werten (n normalerweise >=10000) [Gesetz der großen Zahlen]
 * - die Anzahl an Werteklassen: Wie viele Werteklassen (Wertecluster) sollen erzeugt werden (je feiner desto granularer) - der Wert sollte zwischen 10 und 100 liegen, je nach Verteilungsfunktion
 * 
 * Update 20.06.2016: Diese Klasse muss übearbeitet werden, weil die Schnittstelle zwischen AR-Modell und APV-Methode anders als angenommen gestaltet wird
 * Update 29.06.2016: Klasse vollständig überarbeitet und Testfall geschrieben
*/
public class Distribution {
	//Werte der Verteilung mit den Dimensionen [perioden] [Werte klassiert nach Wertebereichen]
	double [] values;
	//Gibt Prognose an, die der Verteilung zugrunde liegt
	double [] prognosis;
	//gibt an, bei welchem Wert der Wertebereich startet - mit Hilfe von intervalLength ist Wertebereich bekannt
	double [] intervalStartValues;
	
	private static final Logger logger = Logger.getLogger("Distribution.class");
	
	public Distribution (int length, double [] prognosis) {
		if (length<1) length =1;
		this.values = new double [length];
		Arrays.sort(prognosis);
		this.prognosis = prognosis;
		this.setIntervalStartValues();
		this.addValues(prognosis);
	}
	//Methode fügt Werte zu zwei-dimensionalem Array values hinzu
	//wird für jede Periode einmal aufgerufen und prüft, in welchen Wertebereich ein Wert gehört.
	public void addValues(double [] addedValues) {
		int temp=0;		
		for (int i=0; i<addedValues.length; i++) {
			try {
			while (addedValues[i]>this.intervalStartValues[temp]+this.getIntervalLength()) {
				temp++;
			}
			//Try catch is just for debugging purposess
			} catch(Exception e) {
				logger.debug("addedValue: " + addedValues[i] + "; temp: " + temp + "; intervalEndValue: " + (this.intervalStartValues[this.intervalStartValues.length-1]+this.getIntervalLength()) + "; intervalLength: " + this.getIntervalLength() + "; first interval start: " + this.getIntervalStartValues()[0] + "; last interval start: " + this.getIntervalStartValues()[this.getIntervalStartValues().length-1] + "; min value: " + this.getMinValue());
				for (int j=0; j<this.intervalStartValues.length; j++) {
					logger.debug(intervalStartValues[j]);
				}
			}
			this.values[temp]++;
		}
	}
	
	public double [] getValues () {
		return this.values;
	}
	
	public void setIntervalStartValues() {
		int numberOfValueClasses = this.values.length;
		double [] intervalValues = new double [numberOfValueClasses];
        for (int i=0; i<numberOfValueClasses; i++) {
        	/*
        	 * rounding had to be added due to sum computational calculation errors
        	 * the computer returned periodic results for some calculations, yet unexplainable
        	 */
        	intervalValues[i]=Math.ceil(10*(this.getMinValue()+i*this.getIntervalLength()))/10;
        }
        this.intervalStartValues = intervalValues;
	}
	public double [] getIntervalStartValues() {
		return this.intervalStartValues;
	}
	public double getIntervalLength () {
		return this.getDifference()/this.values.length;
	}

	public double getMinValue() {
		return this.prognosis[0];
	}
	public double getMaxValue() {
		return this.prognosis[prognosis.length-1];
	}
	public double getDifference() {
		return this.getMaxValue()-this.getMinValue();
	}
	
	public String getValueRange (int i) {
		return this.getIntervalStartValues()[i] + " - " + (Math.ceil(10*(this.getIntervalStartValues()[i] + this.getIntervalLength()))/10);
	}

}
