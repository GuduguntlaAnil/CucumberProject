package com.automation.workware.runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"@Rerun/failedrerun.txt"},
		glue = {"com.automation.workware.stepdefinations", "com.automation.workware.hooks"},
		plugin = {"pretty","rerun:Rerun/failedrerun.txt"},
		monochrome=true
		)
public class FailedTestRunner extends AbstractTestNGCucumberTests{
	 @DataProvider(parallel=true)
	    @Override
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}
