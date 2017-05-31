package dhbw.ka.mwi.businesshorizon2.cf.entity;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dhbw.ka.mwi.businesshorizon2.cf.JUnitHelper;
import dhbw.ka.mwi.businesshorizon2.cf.TestData;
import dhbw.ka.mwi.businesshorizon2.cf.TestDataProvider;
import dhbw.ka.mwi.businesshorizon2.cf.equity.LazyEquityAlgorithm;

@RunWith(Parameterized.class)
public class TestLazyEntity {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Parameters
	public static List<TestData[]> params() {
		return JUnitHelper.toJunitParams(TestDataProvider.getTestData());
	}

	private final TestData data;

	private final EntityResult result;

	public TestLazyEntity(final TestData data) {
        this.data = data;
		result = new LazyEntityAlgorithm().calculate(data.getParameters());
	}

	@Test
	public void testWacc() throws Exception {
		assertEquals(data.getWacc(), result.getWACC(0), 0.01);
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
