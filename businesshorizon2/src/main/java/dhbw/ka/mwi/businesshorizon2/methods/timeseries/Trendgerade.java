package dhbw.ka.mwi.businesshorizon2.methods.timeseries;
/**
 * Klasse zur Berechnung der Trendgerade mit der Formel f(x) = m*x +b
 * Annahme: Erster Wert ist das Ergebnis von f(1)
 * @author Philipp Nagel
 *
 */
public class Trendgerade {
	private double m;
	private double b;
	public static Trendgerade getTrendgerade(double[] zeitreihe) {
		boolean improvement = true;
		//trendgerade erzeugen
		Trendgerade tG = new Trendgerade();
		//m und b mit Startwerten belegen
		tG.m = zeitreihe[1] - zeitreihe[0];
		tG.b = zeitreihe[0] - tG.m;

		double autokovarianz;
		
		double stepsize= tG.b/10;
		while(true){
			autokovarianz = getAutoKoVarianz(tG.m, tG.b, zeitreihe);
			//m+stepsize und b gleich
			double autokovarianzNew = getAutoKoVarianz((tG.m + stepsize), tG.b, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m += stepsize;
				continue;
			}
			//m+stepsize und b-stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m + stepsize), (tG.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m += stepsize;
				tG.b -= stepsize;
				continue;
			}
			//m+stepsize und b+stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m + stepsize), (tG.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m += stepsize;
				tG.b += stepsize;
				continue;
			}
			//m-stepsize und b gleich
			autokovarianzNew = getAutoKoVarianz((tG.m - stepsize), (tG.b), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m -= stepsize;
				continue;
			}
			//m-stepsize und b+stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m - stepsize), (tG.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m -= stepsize;
				tG.b += stepsize;
				continue;
			}
			//m-stepsize und b-stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m - stepsize), (tG.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m -= stepsize;
				tG.b -= stepsize;
				continue;
			}
			//m gleich und b-stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m), (tG.b - stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.b -= stepsize;
				continue;
			}
			//m gleich und b+stepsize
			autokovarianzNew = getAutoKoVarianz((tG.m ), (tG.b + stepsize), zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.b += stepsize;
				continue;
			}
			//mit m runden berechnen 
			double mRounded = Math.round(tG.m);
			double bRounded = Math.round(tG.b);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m = mRounded;
				tG.b = bRounded;
				continue;
			}
			//mit b runden berechnen
			bRounded = Math.round(tG.b);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.b = bRounded;
				continue;
			}
			//mit m runden berechnen
			mRounded = Math.round(tG.m);
			autokovarianzNew = getAutoKoVarianz(mRounded, bRounded, zeitreihe);
			if(autokovarianzNew<autokovarianz){
				tG.m = mRounded;
				continue;
			}
			
			if(stepsize <=0.01){
				break;
			}else{
				stepsize = stepsize/2;
			}
		}
		
		return tG;
	}

	private static double getAutoKoVarianz(double m2, double b2, double[] urspruenglicheZeitreihe) {
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

	public double getM() {
		// TODO Auto-generated method stub
		return this.m;
	}
	
	public double getB(){
		return this.b;
	}

}
