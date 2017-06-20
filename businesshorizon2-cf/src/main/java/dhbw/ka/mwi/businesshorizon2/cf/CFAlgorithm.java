package dhbw.ka.mwi.businesshorizon2.cf;

@FunctionalInterface
public interface CFAlgorithm<T extends CFResult> {

    T calculateUWert(CFParameter parameter);

}
