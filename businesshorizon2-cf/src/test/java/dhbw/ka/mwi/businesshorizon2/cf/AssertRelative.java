package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Assert;

public class AssertRelative {

    private static int precision = 10000;

    public static void assertRelative(double expected, double actual){
        Assert.assertEquals(expected,actual,Math.abs(expected / precision));
    }

}
