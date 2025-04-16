package MyTestRunners;

import org.testng.annotations.DataProvider;

import io.cucumber.*;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = {"src/test/resources/Features"},
		glue = {"stepDefination", "Hooks"},
		plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:Rerun/failedrerun.txt"},
		monochrome=true
		//tags="not @Skip"
		)

public class TestRunner extends AbstractTestNGCucumberTests {
	 @DataProvider(parallel=true)
	    @Override
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}
