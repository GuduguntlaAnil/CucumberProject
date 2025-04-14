package stepDefination;

import java.util.List;
import java.util.Map;

import com.pages.HomePage;
import com.pages.LoginPage;
import com.qa.Factory.DriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class HomePageSteps {
	private LoginPage loginPage=new LoginPage(DriverFactory.getDriver());
	private HomePage homePage;

@Given("user has already logged in to application")
public void user_has_already_logged_in_to_application(DataTable credTable) {
	List<Map<String, String>> credList = credTable.asMaps();
	String userName = credList.get(0).get("username");
	String password = credList.get(0).get("password");
	
	DriverFactory.getDriver().get("https://www.saucedemo.com/v1/");
	homePage=loginPage.doLogin(userName, password);
 }

@Given("user is on Home page")
public void user_is_on_home_page() {
	Assert.assertTrue(homePage.getProductText().equals("Products"));
	
    
}

@When("user clicks on menu")
public void user_clicks_on_menu() {
	homePage.clicksOnMenu();
	
   
}

@Then("{string} should be visible to user")
public void should_be_visible_to_user(String string) {
    
    Assert.assertTrue(homePage.isLogoutButtonDisplayed());
}


@Then("user gets filter section")
public void user_gets_filter_section(DataTable filterTable) {
	List<String> expFilterList = filterTable.asList();
	
	System.out.println("Expected filter list: " + expFilterList);

	List<String> actualFilterList = homePage.getFilterList();
	System.out.println("Actual accounts section list: " + actualFilterList);

	Assert.assertTrue(expFilterList.containsAll(actualFilterList));
   
}

@Then("filter section count should be {int}")
public void filter_section_count_should_be(Integer filterCount) {
	Assert.assertTrue(homePage.getfilterCount()==filterCount);
    
}



}
