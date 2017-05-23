package dhbw.ka.mwi.businesshorizen2.cf.parameter;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import dhbw.ka.mwi.businesshorizon2.cf.parameter.Bilanz;

public class TestBilanz {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Ignore("Temporarily disabled for Stochi")
	@Test
	public void testAusgeglichen() {
		exception.expect(IllegalArgumentException.class);
		new Bilanz(2000, 1000, 1000, 2000, 1000);
	}

}
