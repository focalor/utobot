package nl.focalor.utobot.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilTest {
	@Test
	public void round() {
		// Test
		double result = MathUtil.round(15.34121, 2);

		// Verify
		assertEquals(15.34d, result, 0d);
	}

	@Test
	public void roundUp() {
		// Test
		double result = MathUtil.round(15.3456, 2);

		// Verify
		assertEquals(15.35d, result, 0d);
	}
}
