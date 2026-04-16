package com.automation.workware.stepdefinations;

import java.util.List;
import java.util.Map;

import com.automation.workware.factory.DriverFactory;
import com.automation.workware.pages.CartPage;
import com.automation.workware.pages.CheckoutCompletePage;
import com.automation.workware.pages.CheckoutOverviewPage;
import com.automation.workware.pages.CheckoutPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class CheckoutSteps {
    private CartPage cartPage = new CartPage(DriverFactory.getDriver());
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage overviewPage;
    private CheckoutCompletePage completePage;

    @When("user clicks checkout")
    public void user_clicks_checkout() {
        checkoutPage = cartPage.clickCheckout();
    }

    @When("user enters checkout information")
    public void user_enters_checkout_information(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        String first = rows.get(0).get("firstName");
        String last = rows.get(0).get("lastName");
        String postal = rows.get(0).get("postalCode");
        checkoutPage.enterCheckoutInformation(first, last, postal);
    }

    @When("user continues checkout")
    public void user_continues_checkout() {
        overviewPage = checkoutPage.clickContinue();
    }

    @Then("the overview should contain the item {string}")
    public void the_overview_should_contain_the_item(String productName) {
        List<String> names = overviewPage.getOverviewItemNames();
        Assert.assertTrue("Overview does not contain expected item", names.contains(productName));
    }

    @Then("the overview total should be displayed")
    public void the_overview_total_should_be_displayed() {
        String total = overviewPage.getSummaryTotal();
        Assert.assertNotNull("Overview total not found", total);
        Assert.assertTrue("Overview total seems empty", total.trim().length() > 0);
    }

    @When("user finishes checkout")
    public void user_finishes_checkout() {
        completePage = overviewPage.clickFinish();
    }

    @When("user clicks cancel on checkout")
    public void user_clicks_cancel_on_checkout() {
        // cancel can be clicked from checkout step one
        if (checkoutPage != null) {
            checkoutPage.clickCancel();
        } else if (overviewPage != null) {
            overviewPage.clickCancel();
        }
        // refresh cartPage reference
        cartPage = new CartPage(DriverFactory.getDriver());
    }

    @Then("user should be back on cart page")
    public void user_should_be_back_on_cart_page() {
        Assert.assertTrue("Not on cart page after cancel", cartPage.isOnCartPage());
    }

    @Then("checkout should be completed")
    public void checkout_should_be_completed() {
        Assert.assertTrue(completePage.isOnCompletePage());
    }
}