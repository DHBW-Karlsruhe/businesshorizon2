package dhbw.ka.mwi.businesshorizon2.cf.entity;

import dhbw.ka.mwi.businesshorizen2.cf.JUnitHelper;
import dhbw.ka.mwi.businesshorizen2.cf.TestData;
import dhbw.ka.mwi.businesshorizen2.cf.TestDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestEntityUeberschuss {

	@Parameters
	public static List<TestData[]> params() {
		return JUnitHelper.toJunitParams(TestDataProvider.getTestData());
	}

	private final TestData data;

	public TestEntityUeberschuss(final TestData data) {
        this.data = data;
	}

	@Test
	public void testUeberschuss() throws Exception {
		assertEquals(data.getEntityUeberschuss(), new EntityUeberschuss().getUberschussGroesse(data.getParameters(), 1),
				0.01);
	}

}
