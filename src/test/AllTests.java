package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestingCalcCumulativePercentRetained.class, TestingCalcFinenessModulus.class,
		TestingCalcPercentFiner.class, TestingCalcPercentRetained.class, TestingCalcUniformityCoefficient.class })
public class AllTests {

}
