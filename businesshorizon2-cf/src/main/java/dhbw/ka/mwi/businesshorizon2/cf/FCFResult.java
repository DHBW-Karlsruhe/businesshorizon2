package dhbw.ka.mwi.businesshorizon2.cf;

public class FCFResult extends CFResult {
    private final double gk;

    FCFResult(final double uWert, final double gk) {
        super(uWert);
        this.gk = gk;
    }

    public double getGk() {
        return gk;
    }
}
