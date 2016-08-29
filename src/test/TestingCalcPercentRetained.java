package test;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import application.SieveAnalysisCalc;

public class TestingCalcPercentRetained {
	@Test
	public void testCase1() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(28.50, 317.60);
		assertTrue("8.97".equals(String.format("%.2f", output)));
	}
	
	@Test
	public void testCase2() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(53.10, 317.60);
		assertTrue("16.72".equals(String.format("%.2f", output)));
	}
	

	@Test
	public void testCase3() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(118.00, 317.60);
		assertTrue("37.15".equals(String.format("%.2f", output)));
	}	

	@Test
	public void testCase4() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(69.90, 317.60);
		assertTrue("22.01".equals(String.format("%.2f", output)));
	}	

	@Test
	public void testCase5() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(25.30, 317.60);
		assertTrue("7.97".equals(String.format("%.2f", output)));
	}	

	@Test
	public void testCase6() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(15.60, 317.60);
		assertTrue("4.91".equals(String.format("%.2f", output)));
	}	

	@Test
	public void testCase7() {
		double output = SieveAnalysisCalc.CalcPerecentRetained(7.20, 317.60);
		assertTrue("2.27".equals(String.format("%.2f", output)));
	}	
}
