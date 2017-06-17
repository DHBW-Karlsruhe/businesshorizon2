package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class CFParameter {

    private final double[] FK;

    private final double EKKosten;
    private final double uSteusatz;
    private final double FKKosten;

    public CFParameter(final double[] FK, final double EKKosten, final double uSteusatz, final double FKKosten) {
    	if(FK[FK.length - 1] != FK[FK.length - 2]){
    		throw new IllegalArgumentException("Letzte beide FKs müssen identisch sein");
    	}
        this.FK = FK;
        this.EKKosten = EKKosten;
        this.uSteusatz = uSteusatz;
        this.FKKosten = FKKosten;
    }


    double[] getFK() {
        return FK;
    }

    double getEKKosten() {
        return EKKosten;
    }

    double getuSteusatz() {
        return uSteusatz;
    }

    double getFKKosten() {
        return FKKosten;
    }

    int numPerioden(){
        return FK.length;
    }
}
