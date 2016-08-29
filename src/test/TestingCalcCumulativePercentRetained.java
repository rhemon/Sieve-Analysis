package test;

import static org.junit.Assert.*;

import org.junit.Test;
import application.SieveAnalysisCalc;

public class TestingCalcCumulativePercentRetained {

	@Test
	public void testCase1() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00);
		assertTrue("0.00".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase2() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27);
		assertTrue("9.27".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase3() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27, 18.66);
		assertTrue("27.93".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase4() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27, 18.66, 34.80);
		assertTrue("62.73".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase5() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27, 18.66, 34.80, 25.43);
		assertTrue("88.16".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase6() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27, 18.66, 34.80, 25.43, 8.96);
		assertTrue("97.12".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase7() {
		double output = SieveAnalysisCalc.CaclCumulativePercentRetained(0.00, 9.27, 18.66, 34.80, 25.43, 8.96, 2.26);
		assertTrue("99.38".equals(String.format("%.2f", output)));
	}
	

}
