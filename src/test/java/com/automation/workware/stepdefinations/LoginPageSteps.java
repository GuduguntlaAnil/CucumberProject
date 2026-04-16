package com.automation.workware.stepdefinations;

import com.automation.workware.factory.DriverFactory;
import com.automation.workware.pages.HomePage;
import com.automation.workware.pages.LoginPage;
import com.automation.workware.utils.ConfigReader;
import com.automation.workware.utils.ExcelReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Step definitions for login-related scenarios.
 *
 * This class maps Gherkin steps used in feature files to actions performed via
 * the Page Objects (LoginPage, HomePage). It also contains helper steps that
 * integrate with the ExcelReader for data-driven scenarios.
 */
public class LoginPageSteps {
	private String title;
	private LoginPage loginPage=new LoginPage(DriverFactory.getDriver());

/**
 * Navigate to the application login page using the configured base.url.
 */
@Given("user is on login page")
public void user_is_on_login_page() {
	ConfigReader cr = new ConfigReader();
	Properties prop = cr.init_prop();
	String baseUrl = prop.getProperty("base.url");
	if (baseUrl == null || baseUrl.isEmpty()) {
		baseUrl = "https://www.saucedemo.com/v1/"; // fallback
	}
	DriverFactory.getDriver().get(baseUrl);
}

/**
 * Read and store the page title for later verification.
 */
@When("user gets the title of the page")
public void user_gets_the_title_of_the_page() {
	 title=loginPage.getLoginPageTitle();
	    System.out.println("page title is :"+ title);
}

/**
 * Assert that the title contains the expected text.
 */
@Then("page title should be {string}")
public void page_title_should_be(String expectedTitleName) {
   
    Assert.assertTrue(title.contains(expectedTitleName));

}

/**
 * Verify that the accepted usernames help text is visible.
 */
@Then("Accepted UserNames Text should be displayed")
public void accepted_userNames_text_should_be_displayed() {
	
	Assert.assertTrue(loginPage.isacceptedUserNamesTextExists() );
	
    
}

/**
 * Enter username into username field.
 */
@When("user enters username {string}")
public void user_enters_username(String userName) {
	loginPage.enterUserName(userName);
}

/**
 * Enter password into password field.
 */
@When("user enters password {string}")
public void user_enters_password(String pwd) {
  loginPage.enterPassword(pwd);
   
}

/**
 * Click the login button to attempt authentication.
 */
@When("user clicks on Login button")
public void user_clicks_on_login_button() {
   loginPage.clickOnLogin();
    
}

/**
 * Verify that the current URL contains a specific fragment (used to check navigation).
 */
@Then("page url contains {string}")
public void page_url_contains(String endurl) {
	Assert.assertTrue(loginPage.getSwaglabsUrl().contains(endurl));
    
}

// --- New steps to support Excel-driven data ---

/**
 * Log in using credentials read from an Excel sheet row.
 * sheetName: Excel sheet name
 * rowNum: 1-based row number as seen in Excel (header is row 1)
 */
@When("user logs in using excel sheet {string} row {int}")
public void user_logs_in_using_excel_sheet_row(String sheetName, int rowNum) {
    // rowNum here is the Excel row number as a human would see it (1-based, where row 1 is the header row).
    // Convert to zero-based index expected by ExcelReader.getRowDataAsMap (header at index 0).
    ConfigReader cr = new ConfigReader();
    Properties prop = cr.init_prop();
    String excelPath = prop.getProperty("excel.path");
    ExcelReader er = new ExcelReader(excelPath);
    int rowIndex = Math.max(0, rowNum - 1); // ensure non-negative
    Map<String, String> row = er.getRowDataAsMap(sheetName, rowIndex);
    String userName = row.getOrDefault("username", row.getOrDefault("user", ""));
    String pwd = row.getOrDefault("password", row.getOrDefault("pwd", ""));
    loginPage.doLogin(userName, pwd);
}

/**
 * Iterate through all data rows in a sheet and perform login for each.
 * This method demonstrates a simple data-driven flow using ExcelReader.getAllData().
 */
@When("user performs login for all rows in sheet {string}")
public void user_performs_login_for_all_rows_in_sheet(String sheetName) throws InterruptedException {
    ConfigReader cr = new ConfigReader();
    Properties prop = cr.init_prop();
    String excelPath = prop.getProperty("excel.path");
    ExcelReader er = new ExcelReader(excelPath);
    List<Map<String, String>> rows = er.getAllData(sheetName);
    HomePage homePage = null;
    for (Map<String, String> row : rows) {
        String userName = row.getOrDefault("username", row.getOrDefault("user", ""));
        String pwd = row.getOrDefault("password", row.getOrDefault("pwd", ""));
        homePage = loginPage.doLogin(userName, pwd);
        // quick verification and logout before next iteration
        Thread.sleep(500); // small pause to allow page transition
        if (homePage != null && homePage.isLogoutButtonDisplayed()) {
            homePage.clickLogout();
        }
    }
}


}