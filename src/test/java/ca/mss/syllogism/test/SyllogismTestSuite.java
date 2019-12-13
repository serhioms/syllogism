package ca.mss.syllogism.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.ctalkobt.SyllogismTest;

@RunWith(Suite.class)
@SuiteClasses({ SyllogismTest.class, All24AssertiveSyllogismTest.class })
public class SyllogismTestSuite {

}
