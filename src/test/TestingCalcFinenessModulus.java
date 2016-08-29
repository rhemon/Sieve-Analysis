package test;

import static org.junit.Assert.*;
import application.SieveAnalysisCalc;
import org.junit.Test;

public class TestingCalcFinenessModulus {

	@Test
	public void testCase1() {
		double output = SieveAnalysisCalc.CalcFinenessModulus(0.00, 8.97, 25.69, 62.85, 84.86, 92.82);
		assertTrue("2.75".equals(String.format("%.2f", output)));
	}
	
	@Test
	public void testCase2() {
		double output = SieveAnalysisCalc.CalcFinenessModulus(0.00,
															  9.27,
															  27.93,
															  62.73,
															  88.16,
															  97.12);
		assertTrue("2.85".equals(String.format("%.2f", output)));
	}
}
