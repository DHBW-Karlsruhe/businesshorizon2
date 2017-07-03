package dhbw.ka.mwi.businesshorizon2.ar;

import org.junit.Assert;

public class AssertRelative {

    private static int precision = 10000;

    public static void assertRelative(double expected, double actual){
        Assert.assertEquals(expected,actual,Math.abs(expected / precision));
    }
    

    public static void assertRelative(double[] expected, double[] actual){
        Assert.assertArrayEquals(expected,actual,Math.abs(expected[0] / precision));
    }

}
