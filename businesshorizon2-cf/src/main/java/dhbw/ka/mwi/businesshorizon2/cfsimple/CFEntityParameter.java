package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class CFEntityParameter extends CFParameter {

    private final double[] FCF;

    public CFEntityParameter(final double[] FCF, final double[] FK, final double EKKosten, final double uSteusatz, final double FKKosten) {
        super(FK,EKKosten,uSteusatz,FKKosten);
        this.FCF = FCF;
    }

    double[] getFCF() {
        return FCF;
    }

}
