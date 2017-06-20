package dhbw.ka.mwi.businesshorizon2.cf;

class CFIntermediateResult {

    private final double[] uWert;
    private final double[] ekKost;

    CFIntermediateResult(final double[] uWert, final double[] ekKost) {
        this.uWert = uWert;
        this.ekKost = ekKost;
    }

    double[] getuWert() {
        return uWert;
    }

    double[] getEkKost() {
        return ekKost;
    }
}
