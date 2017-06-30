package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.cf.AssertRelative.assertRelative;

public class TestTaxShieldEKKostVerschCalculator2 {

    @Test
    public void testTaxShieldEKKostVerschCalculator() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        final CFIntermediateResult result = new CFIntermediateResult(new double[]{1055.24,1053.75,1043.08,1000,1000},null);
        final EKKostVerschCalculator calculator = new TaxShieldEKKostVerschCalculator2();
        assertRelative(0.1164268,calculator.calculateEKKostenVersch(parameter,result,1));
        assertRelative(0.1175229,calculator.calculateEKKostenVersch(parameter,result,2));
        assertRelative(0.1178684,calculator.calculateEKKostenVersch(parameter,result,3));
        assertRelative(0.12,calculator.calculateEKKostenVersch(parameter,result,4));
    }


}
