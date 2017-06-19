package dhbw.ka.mwi.businesshorizon2.cfsimple;

public class APVResult extends CFResult{

    private final double uwFiktiv;
    private final double taxShield;
    private final double gk;

    APVResult(final double uWert, final double uwFiktiv, final double taxShield, final double gk) {
        super(uWert);
        this.uwFiktiv = uwFiktiv;
        this.taxShield = taxShield;
        this.gk = gk;
    }

    public double getUwFiktiv() {
        return uwFiktiv;
    }

    public double getTaxShield() {
        return taxShield;
    }

    public double getGk() {
        return gk;
    }
}
