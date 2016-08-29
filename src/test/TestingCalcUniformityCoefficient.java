package test;

import static org.junit.Assert.*;

import org.junit.Test;
import application.SieveAnalysisCalc;
public class TestingCalcUniformityCoefficient {

	@Test
	public void testCase1() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(0.95, 0.26);
		assertTrue("3.65".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase2() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(1.10, 0.34);
		assertTrue("3.24".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase3() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(0.90, 0.24);
		assertTrue("3.75".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase4() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(1.15, 0.34);
		assertTrue("3.38".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase5() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(0.70, 0.19);
		assertTrue("3.68".equals(String.format("%.2f", output)));
	}
	@Test
	public void testCase6() {
		double output = SieveAnalysisCalc.CalcUniformityCoefficient(0.89, 0.32);
		assertTrue("2.78".equals(String.format("%.2f", output)));
	}
}
