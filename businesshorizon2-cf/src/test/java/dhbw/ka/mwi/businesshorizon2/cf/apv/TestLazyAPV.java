package dhbw.ka.mwi.businesshorizon2.cf.apv;

import dhbw.ka.mwi.businesshorizon2.cf.JUnitHelper;
import dhbw.ka.mwi.businesshorizon2.cf.TestData;
import dhbw.ka.mwi.businesshorizon2.cf.TestDataProvider;
import dhbw.ka.mwi.businesshorizon2.cf.equity.LazyEquityAlgorithm;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestLazyAPV {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Parameters
	public static List<TestData[]> params() {
		return JUnitHelper.toJunitParams(TestDataProvider.getTestData());
	}

	private final TestData data;

	private final APVResult result;

	public TestLazyAPV(final TestData data) {
        this.data = data;
		result = new LazyAPVAlgorithm().calculate(data.getParameters());
	}

	@Test
	public void testTaxShield() throws Exception {
		assertEquals(data.getTaxShield(), result.getTaxShield(0), 0.01);
	}

	@Test
	public void testUnternehmenswert() throws Exception {
		assertEquals(data.getuWert(), result.getUnternehmenswertNow(), 0.01);
	}

	@Test
	public void testNullParameters() throws Exception {
		exception.expect(IllegalArgumentException.class);
		new LazyEquityAlgorithm().calculate(null);
	}

}
