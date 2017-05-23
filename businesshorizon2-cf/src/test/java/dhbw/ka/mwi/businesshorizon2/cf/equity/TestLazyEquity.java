package dhbw.ka.mwi.businesshorizon2.cf.equity;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dhbw.ka.mwi.businesshorizen2.cf.JUnitHelper;
import dhbw.ka.mwi.businesshorizen2.cf.TestData;
import dhbw.ka.mwi.businesshorizen2.cf.TestDataProvider;

@RunWith(Parameterized.class)
public class TestLazyEquity {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Parameters
	public static List<TestData[]> params() {
		return JUnitHelper.toJunitParams(TestDataProvider.getTestData());
	}

	private final TestData data;

	private final EquityResult result;

	public TestLazyEquity(final TestData data) {
        this.data = data;
		result = new LazyEquityAlgorithm().calculate(data.getParameters());
	}

	@Test
	public void testUnternehmenswert() throws Exception {
		assertEquals(data.getuWert(), result.getUnternehmenswertNow(), 0.01);
	}

	@Test
	public void testEKKostVersch() throws Exception {
		assertEquals(data.getEkKostVersch(), result.getEKKostVersch(0), 0.01);
	}

	@Test
	public void testNullParameters() throws Exception {
		exception.expect(IllegalArgumentException.class);
		new LazyEquityAlgorithm().calculate(null);
	}

}
