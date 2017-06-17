package dhbw.ka.mwi.businesshorizon2.cfsimple;

class CFIntermediateResult {

    private final double[] uWert;
    private final double[] gk;
    private final double[] ekKost;

    CFIntermediateResult(final double[] uWert, final double[] gk, final double[] ekKost) {
        this.uWert = uWert;
        this.gk = gk;
        this.ekKost = ekKost;
    }

    double[] getuWert() {
        return uWert;
    }

    double[] getGk() {
        return gk;
    }

    double[] getEkKost() {
        return ekKost;
    }
}
