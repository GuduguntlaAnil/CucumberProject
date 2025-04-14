package stepDefination;

import com.pages.LoginPage;
import com.qa.Factory.DriverFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class LoginSteps {
	private String title;
	private LoginPage loginPage=new LoginPage(DriverFactory.getDriver());

@Given("user is on login page")
public void user_is_on_login_page() {
	DriverFactory.getDriver().get("https://www.saucedemo.com/v1/");
}

@When("user gets the title of the page")
public void user_gets_the_title_of_the_page() {
	 title=loginPage.getLoginPageTitle();
	    System.out.println("page title is :"+ title);
}

@Then("page title should be {string}")
public void page_title_should_be(String expectedTitleName) {
   
    Assert.assertTrue(title.contains(expectedTitleName));

}

@Then("Accepted UserNames Text should be displayed")
public void accepted_userNames_text_should_be_displayed() {
	Assert.assertTrue(loginPage.isacceptedUserNamesTextExists() );
    
}

@When("user enters username {string}")
public void user_enters_username(String userName) {
	loginPage.enterUserName(userName);
}

@When("user enters password {string}")
public void user_enters_password(String pwd) {
  loginPage.enterPassword(pwd);
   
}

@When("user clicks on Login button")
public void user_clicks_on_login_button() {
   loginPage.clickOnLogin();
    
}

@Then("page url contains {string}")
public void page_url_contains(String endurl) {
	Assert.assertTrue(loginPage.getSwaglabsUrl().contains(endurl));
    
}




}
