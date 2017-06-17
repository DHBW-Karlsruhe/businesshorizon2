package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class CFEquityParameter extends CFParameter {

    private final double[] ausschuettung;

    public CFEquityParameter(final double[] ausschuettung, final double[] FK, final double EKKosten, final double uSteusatz, final double FKKosten) {
        super(FK,EKKosten,uSteusatz,FKKosten);
        this.ausschuettung = ausschuettung;
    }

    double[] getAusschuettung() {
        return ausschuettung;
    }

}
