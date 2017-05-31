package dhbw.ka.mwi.businesshorizon2.cf;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.Bilanz;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFPeriode;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.GUV;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TestDataProvider {
	private TestDataProvider() {
	}

	public static Collection<TestData> getTestData() {
		final Collection<TestData> testdata = new ArrayList<>();
		testdata.add(getTestExample());
		testdata.add(getTestNegative());
		testdata.add(getTestZero());

		return testdata;
	}

	private static TestData getTestExample() {
		final List<CFPeriode> perioden = new ArrayList<>();
		perioden.add(new CFPeriode(null, new Bilanz(2280, 200, 720, 1260, 500)));
		perioden.add(new CFPeriode(new GUV(2200, 1459.98, 400), new Bilanz(2320, 200, 720, 1300, 500)));
		perioden.add(new CFPeriode(new GUV(2200, 1494.70, 360), new Bilanz(1920, 300, 720, 1000, 500)));
		perioden.add(new CFPeriode(new GUV(3000, 1417.46, 320), new Bilanz(2220, 400, 720, 1400, 500)));
		perioden.add(new CFPeriode(new GUV(2000, 1400.34, 280), new Bilanz(2220, 400, 720, 1400, 500)));

		return new TestData(new CFParameter(10.0582d / 100, 35d / 100, 15d / 100, 25d / 100, 8d / 100, perioden),
				1611.97, 7.10 / 100, 513.38, 9.07 / 100, 176.76, 146.83);
	}

	private static TestData getTestNegative() {
		final List<CFPeriode> perioden = new ArrayList<>();
		perioden.add(new CFPeriode(null, new Bilanz(2280, 200, 720, 1260, 500)));
		perioden.add(new CFPeriode(new GUV(100, 1459.98, 400), new Bilanz(2320, 200, 720, 1300, 500)));
		perioden.add(new CFPeriode(new GUV(-100, 1494.70, 360), new Bilanz(1920, 300, 720, 1000, 500)));
		perioden.add(new CFPeriode(new GUV(100, 1417.46, 320), new Bilanz(2220, 400, 720, 1400, 500)));
		perioden.add(new CFPeriode(new GUV(2000, 1400.34, 280), new Bilanz(2220, 400, 720, 1400, 500)));
		return new TestData(new CFParameter(10.0582d / 100, 35d / 100, 15d / 100, 25d / 100, 8d / 100, perioden),
				-1640.06, 17.34 / 100, 513.38, 7.54 / 100, -1161.99, -1191.92);
	}

	private static TestData getTestZero() {
		final List<CFPeriode> perioden = new ArrayList<>();
		perioden.add(new CFPeriode(null, new Bilanz(0, 0, 0, 0, 0)));
		perioden.add(new CFPeriode(new GUV(0, 1, 0), new Bilanz(0, 0, 0, 0, 0)));
		perioden.add(new CFPeriode(new GUV(0, 1, 0), new Bilanz(0, 0, 0, 0, 0)));
		perioden.add(new CFPeriode(new GUV(0, 1, 0), new Bilanz(0, 0, 0, 0, 0)));
		perioden.add(new CFPeriode(new GUV(0, 1, 0), new Bilanz(0, 0, 0, 0, 0)));
		return new TestData(new CFParameter(10.0582d / 100, 35d / 100, 15d / 100, 25d / 100, 8d / 100, perioden), -6.34,
				8.30 / 100, 0.0, 8.30 / 100, -0.64, -0.64);
	}
}
