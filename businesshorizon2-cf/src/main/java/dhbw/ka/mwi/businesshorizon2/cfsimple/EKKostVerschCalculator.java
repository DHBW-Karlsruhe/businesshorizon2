package dhbw.ka.mwi.businesshorizon2.cfsimple;

@FunctionalInterface
interface EKKostVerschCalculator {

    double calculateEKKostenVersch(final CFParameter parameter, final CFIntermediateResult intermediate, final int periode);

}
