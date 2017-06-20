package dhbw.ka.mwi.businesshorizon2.cf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTaxShieldCalculator {

    @Test
    public void testTaxShield() throws Exception {
        final CFParameter parameter = new CFParameter(new double[]{0,138.61,202.31,174.41,202.51},new double[]{1260,1320,1330,1400,1400},0.09969137,0.26325,0.08);
        assertEquals(363.21,TaxShieldCalculator.calculateTaxShield(parameter,0),363.21 / 10000);
        assertEquals(365.73,TaxShieldCalculator.calculateTaxShield(parameter,1),365.73 / 10000);
        assertEquals(367.19,TaxShieldCalculator.calculateTaxShield(parameter,2),367.19 / 10000);
        assertEquals(368.55,TaxShieldCalculator.calculateTaxShield(parameter,3),368.55 / 10000);
        assertEquals(368.55,TaxShieldCalculator.calculateTaxShield(parameter,4),368.55 / 10000);
    }

}
