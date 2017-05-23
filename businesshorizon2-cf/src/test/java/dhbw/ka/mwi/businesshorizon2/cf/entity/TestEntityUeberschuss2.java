package dhbw.ka.mwi.businesshorizon2.cf.entity;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dhbw.ka.mwi.businesshorizen2.cf.JUnitHelper;
import dhbw.ka.mwi.businesshorizen2.cf.TestData;
import dhbw.ka.mwi.businesshorizen2.cf.TestDataProvider;

@RunWith(Parameterized.class)
public class TestEntityUeberschuss2 {

	@Parameters
	public static List<TestData[]> params() {
		return JUnitHelper.toJunitParams(TestDataProvider.getTestData());
	}

	private final TestData data;

	public TestEntityUeberschuss2(final TestData data) {
        this.data = data;
	}

	@Test
	public void testUeberschuss() throws Exception {
		assertEquals(data.getEntityUeberschuss(),
				new EntityUeberschuss2().getUberschussGroesse(data.getParameters(), 1), 0.01);
	}

}
