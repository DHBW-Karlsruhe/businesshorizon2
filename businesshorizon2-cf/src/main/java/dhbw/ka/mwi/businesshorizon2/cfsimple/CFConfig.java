package dhbw.ka.mwi.businesshorizon2.cfsimple;

public final class CFConfig {

    private static int steps = 200;

    private static EKKostVerschCalculator ekKostVerschCalculator = new NastyEKKostVerschCalculator();

    private CFConfig() {
    }

    public static int getSteps() {
        return steps;
    }

    public static void setSteps(final int steps) {
        CFConfig.steps = steps;
    }

    public static EKKostVerschCalculator getEkKostVerschCalculator() {
        return ekKostVerschCalculator;
    }

    public static void setEkKostVerschCalculator(final EKKostVerschCalculator ekKostVerschCalculator) {
        CFConfig.ekKostVerschCalculator = ekKostVerschCalculator;
    }
}
