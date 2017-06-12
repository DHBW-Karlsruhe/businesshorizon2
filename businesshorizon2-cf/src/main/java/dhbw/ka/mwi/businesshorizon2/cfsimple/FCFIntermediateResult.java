package dhbw.ka.mwi.businesshorizon2.cfsimple;

class FCFIntermediateResult {

    private final double[] uWert;
    private final double[] gk;
    private final double[] wacc;
    private final double[] ekKost;

    FCFIntermediateResult(final double[] uWert, final double[] gk, final double[] wacc, final double[] ekKost) {
        this.uWert = uWert;
        this.gk = gk;
        this.wacc = wacc;
        this.ekKost = ekKost;
    }

    double[] getuWert() {
        return uWert;
    }

    double[] getGk() {
        return gk;
    }

    double[] getWacc() {
        return wacc;
    }

    double[] getEkKost() {
        return ekKost;
    }
}
