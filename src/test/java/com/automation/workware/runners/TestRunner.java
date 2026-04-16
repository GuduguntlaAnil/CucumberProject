package com.automation.workware.runners;

import org.testng.annotations.DataProvider;

import io.cucumber.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = {"src/test/resources/features"},
		glue = {"com.automation.workware.stepdefinations", "com.automation.workware.hooks"},
		plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:Rerun/failedrerun.txt"},
		monochrome=true
		//tags="@anil"
		)

/**
 * TestRunner is the Cucumber TestNG runner used to execute feature files.
 *
 * Notes:
 * - Extends AbstractTestNGCucumberTests which exposes scenarios() used by TestNG
 *   for data-driven scenario execution.
 * - The DataProvider is configured with parallel=true to enable parallel execution
 *   of scenarios. Adjust thread counts via TestNG/Maven surefire configuration as needed.
 */
public class TestRunner extends AbstractTestNGCucumberTests {
	 @DataProvider(parallel=true)
	    @Override
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}