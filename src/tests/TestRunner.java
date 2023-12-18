package tests;

import java.util.logging.Logger;

import org.junit.runner.*;
import org.junit.runner.notification.Failure;

public class TestRunner {

	public static void main(String[] args) {

		Result result = JUnitCore.runClasses(CarTireAllTests.class, TireShopAllTests.class);

		Logger l = Logger.getLogger(TestRunner.class.toString());

		for (Failure f : result.getFailures())
			l.warning(f.toString());

		l.info("Time of performed tests: " + result.getRunTime());
		l.info("Number of performed tests: " + result.getRunCount());

		l.info("Number of successful: " + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()
				- result.getAssumptionFailureCount()));
		l.info("Number of failed: " + result.getFailureCount());
		l.info("Number of skipped: " + result.getIgnoreCount());
		l.info("Number of dynamically skipped: " + result.getAssumptionFailureCount());

		if (result.wasSuccessful())
			l.info("All tests were successful!");
		else
			l.warning("There is a mistake in tests!");

	}

}
