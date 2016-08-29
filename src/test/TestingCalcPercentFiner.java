package test;

import static org.junit.Assert.*;

import org.junit.Test;
import application.SieveAnalysisCalc;
public class TestingCalcPercentFiner {

	@Test
	public void testCase1() {
		double output = SieveAnalysisCalc.CalcPercentFiner(9.27);
		assertTrue("90.73".equals(String.format("%.2f", output)));
	}
	
	@Test
	public void testCase2() {
		double output = SieveAnalysisCalc.CalcPercentFiner(27.93);
		assertTrue("72.07".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase3() {
		double output = SieveAnalysisCalc.CalcPercentFiner(62.73);
		assertTrue("37.27".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase4() {
		double output = SieveAnalysisCalc.CalcPercentFiner(88.16);
		assertTrue("11.84".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase5() {
		double output = SieveAnalysisCalc.CalcPercentFiner(97.12);
		assertTrue("2.88".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase6() {
		double output = SieveAnalysisCalc.CalcPercentFiner(99.38);
		assertTrue("0.62".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase7() {
		double output = SieveAnalysisCalc.CalcPercentFiner(0.00);
		assertTrue("100.00".equals(String.format("%.2f", output)));	
	}

}
