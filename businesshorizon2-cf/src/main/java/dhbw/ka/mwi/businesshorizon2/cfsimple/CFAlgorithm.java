package dhbw.ka.mwi.businesshorizon2.cfsimple;

@FunctionalInterface
public interface CFAlgorithm<T extends CFParameter> {

    double calculateUWert(T parameter);

}
