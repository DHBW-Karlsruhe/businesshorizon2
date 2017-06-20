package dhbw.ka.mwi.businesshorizon2.cf;

public final class CFConfig {

    private static double precision = 0.001;

    private static EKKostVerschCalculator ekKostVerschCalculator = new NastyEKKostVerschCalculator();

    private CFConfig() {
    }

    public static EKKostVerschCalculator getEkKostVerschCalculator() {
        return ekKostVerschCalculator;
    }

    public static void setEkKostVerschCalculator(final EKKostVerschCalculator ekKostVerschCalculator) {
        CFConfig.ekKostVerschCalculator = ekKostVerschCalculator;
    }

    public static double getPrecision() {
        return precision;
    }

    public static void setPrecision(final double precision) {
        CFConfig.precision = precision;
    }
}
