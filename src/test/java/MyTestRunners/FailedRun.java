package MyTestRunners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"@Rerun/failedrerun.txt"},
		glue = {"stepDefination", "Hooks"},
		plugin = {"pretty","rerun:Rerun/failedrerun.txt"},
		monochrome=true
		)
public class FailedRun extends AbstractTestNGCucumberTests{
	 @DataProvider(parallel=true)
	    @Override
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}
