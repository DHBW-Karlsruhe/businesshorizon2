package dhbw.ka.mwi.businesshorizon2.cf;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JUnitHelper {

	private JUnitHelper() {
	}

	public static List<TestData[]> toJunitParams(final Collection<TestData> testcases) {
		return testcases.stream().map(testcase -> new TestData[] { testcase }).collect(Collectors.toList());
	}

}
