package dhbw.ka.mwi.businesshorizon2.methods.timeseries;
/**
 * Klasse zur Berechnung der Trendgerade mit der Formel f(x) = m*x +b
 * Annahme: Erster Wert ist das Ergebnis von f(1)
 * @author Philipp Nagel, Jonathan Janke
 * 
 * @param m Steigung der Trendgeraden
 * @param b Anfangswert an der Koordinatenachse der Trendgeraden, wenn x=0
 *
 */
public class Trendgerade {
	private double m;
	private double b;
	/**
	 * @author Philipp Nagel
	 */
	//method to get value from f(x) = m*x+b;
	public double getValue(int x) {
		return this.getM()*x + this.getB();
	}
	
	/**
	 * @author Philipp Nagel
	 */
	public Trendgerade (double[] zeitreihe) {
		//trendgerade erzeugen
		//m und b mit Startwerten belegen
		this.m = zeitreihe[1] - zeitreihe[0];
		this.b = zeitreihe[0] - this.m;

		double autokovarianz;
		
		double stepsize= this.b/10;
		
		//Schleife wird solange iteriert, bis sie über den Befehl "break;" abgebrochen wird s.u.
		while(stepsize > 0.01){
			autokovarianz = getAutoKoVarianz(this.m, this.b, zeitreihe);
			//m+stepsize und b gleich
			double autokovarianzNew = getAutoKoVarianz((this.m + stepsize), this.b, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m += stepsize;
			}
			//m+stepsize und b-stepsize
			autokovarianzNew = getAutoKoVarianz((this.m + stepsize), (this.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m += stepsize;
				this.b -= stepsize;
			}
			//m+stepsize und b+stepsize
			autokovarianzNew = getAutoKoVarianz((this.m + stepsize), (this.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m += stepsize;
				this.b += stepsize;
			}
			//m-stepsize und b gleich
			autokovarianzNew = getAutoKoVarianz((this.m - stepsize), (this.b), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m -= stepsize;
			}
			//m-stepsize und b+stepsize
			autokovarianzNew = getAutoKoVarianz((this.m - stepsize), (this.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m -= stepsize;
				this.b += stepsize;
			}
			//m-stepsize und b-stepsize
			autokovarianzNew = getAutoKoVarianz((this.m - stepsize), (this.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m -= stepsize;
				this.b -= stepsize;
			}
			//m gleich und b-stepsize
			autokovarianzNew = getAutoKoVarianz((this.m), (this.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.b -= stepsize;
			}
			//m gleich und b+stepsize
			autokovarianzNew = getAutoKoVarianz((this.m ), (this.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.b += stepsize;
			}
			//mit m runden berechnen 
			double mRounded = Math.round(this.m);
			double bRounded = Math.round(this.b);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m = mRounded;
				this.b = bRounded;
			}
			//mit b runden berechnen
			bRounded = Math.round(this.b);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.b = bRounded;
			}
			//mit m runden berechnen
			mRounded = Math.round(this.m);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				this.m = mRounded;
			}
			
			stepsize = stepsize/2;
		}
	}
/**
 * Methode um die Autokovarianz in Abhängigkeit von m und b in der Funktion f(x) = m*x+b zu berechnen
 * Dazu wird auch die ursprüngliche Zeitreihe übergeben
 * @param m2
 * @param b2
 * @param urspruenglicheZeitreihe
 * @return Wert der Autokovarianz
 * @author Philipp Nagel
 */
	public static double getAutoKoVarianz(double m2, double b2, double[] urspruenglicheZeitreihe) {
		double autoKovarianz = 0;
		double[] ergebnisse = new double[urspruenglicheZeitreihe.length];
		for(int i=0; i<ergebnisse.length;i++){
			ergebnisse[i] = (m2* (i+1)) + b2;
		}
		for(int i=0; i<ergebnisse.length;i++){
			autoKovarianz = autoKovarianz + ((ergebnisse[i] - urspruenglicheZeitreihe[i]) * (ergebnisse[i] - urspruenglicheZeitreihe[i]));
		}
		return autoKovarianz;
	}
/**
 * gibt den Wert von m zurück
 * @author Philipp Nagel
 */
	public double getM() {
		// TODO Auto-generated method stub
		return this.m;
	}
	/**
	 * gibt den Wert von b zurück
	 * @author Philipp Nagel
	 */	
	public double getB(){
		return this.b;
	}

}
