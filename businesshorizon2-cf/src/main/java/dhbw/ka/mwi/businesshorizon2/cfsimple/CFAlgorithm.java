package dhbw.ka.mwi.businesshorizon2.cfsimple;

@FunctionalInterface
public interface CFAlgorithm<T extends CFResult> {

    T calculateUWert(CFParameter parameter);

}
