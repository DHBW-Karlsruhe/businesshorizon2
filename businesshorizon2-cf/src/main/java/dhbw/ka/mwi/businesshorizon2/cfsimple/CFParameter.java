package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class CFParameter {

    private final double[] FCF;
    private final double[] FK;

    private final double EKKosten;
    private final double uSteusatz;
    private final double FKKosten;

    public CFParameter(final double[] FCF, final double[] FK, final double EKKosten, final double uSteusatz, final double FKKosten) {
        this.FCF = FCF;
        this.FK = FK;
        this.EKKosten = EKKosten;
        this.uSteusatz = uSteusatz;
        this.FKKosten = FKKosten;
    }

    public double[] getFCF() {
        return FCF;
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
        return FCF.length;
    }
}
