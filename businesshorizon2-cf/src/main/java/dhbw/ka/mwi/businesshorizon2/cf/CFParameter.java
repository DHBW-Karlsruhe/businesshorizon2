package dhbw.ka.mwi.businesshorizon2.cf;

public class CFParameter {

    private final double[] FCF;
    private final double[] FTE;
    private final double[] FK;

    private final double EKKosten;
    private final double uSteusatz;
    private final double FKKosten;

    public CFParameter(final double[] FCF, final double[] FK, final double EKKosten, final double uSteusatz, final double FKKosten) {
    	if(FK[FK.length - 1] != FK[FK.length - 2]){
    		throw new IllegalArgumentException("Letzte beide FKs müssen identisch sein");
    	}
        if(FK.length != FCF.length){
            throw new IllegalArgumentException("FK und FCF haben eine unterschiedliche länge");
        }
        this.FCF = FCF;
        this.FK = FK;
        this.EKKosten = EKKosten;
        this.uSteusatz = uSteusatz;
        this.FKKosten = FKKosten;
        FTE = calculateFTE();
    }

    private double[] calculateFTE(){
        final double[] FTE = new double[FCF.length];
        for (int i = 1; i < FCF.length; i++) {
            final double zinsen =  FK[i - 1] * FKKosten;
            final double taxShield = uSteusatz * zinsen;
            FTE[i] = FCF[i] + taxShield - zinsen + (FK[i] - FK[i - 1]);
        }
        return FTE;
    }


    public double[] getFK() {
        return FK;
    }

    public double getEKKosten() {
        return EKKosten;
    }

    public double getuSteusatz() {
        return uSteusatz;
    }

    public double getFKKosten() {
        return FKKosten;
    }

    public int numPerioden(){
        return FK.length;
    }

    public double[] getFCF() {
        return FCF;
    }

    public double[] getFTE() {
        return FTE;
    }
}
