package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static dhbw.ka.mwi.businesshorizon2.cf.AssertRelative.assertRelative;

public class TestTaxShieldCalculator {

    @Test
    public void testTaxShield() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertRelative(363.21,TaxShieldCalculator.calculateTaxShield(parameter,0));
        assertRelative(365.73,TaxShieldCalculator.calculateTaxShield(parameter,1));
        assertRelative(367.19,TaxShieldCalculator.calculateTaxShield(parameter,2));
        assertRelative(368.55,TaxShieldCalculator.calculateTaxShield(parameter,3));
        assertRelative(368.55,TaxShieldCalculator.calculateTaxShield(parameter,4));
    }

}
