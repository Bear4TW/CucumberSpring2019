package com.vytrack.step_definitions;

import com.vytrack.utilities.ConfigurationReader;
import com.vytrack.utilities.Driver;
import com.vytrack.utilities.Pages;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.Map;

public class LoginStepDefinitions {

    Pages pages = new Pages();

    @Given("user is on the landing page")
    public void user_is_on_the_landing_page() {
        pages.loginPage().goToLandingPage();
    }

    @Then("user logs in as a store manager")
    public void user_logs_in_as_a_store_manager() {
        String username = ConfigurationReader.getProperty("storemanagerusername");
        String password = ConfigurationReader.getProperty("storemanagerpassword");
        pages.loginPage().login(username, password);
    }

    @Then("user verifies that {string} page name is displayed")
    public void user_verifies_that_page_name_is_displayed(String string) {
        Assert.assertEquals(string, pages.dashboardPage().getPageSubTitle());
    }
    //this is temporary solution until we start using hooks
//    @Then("user quits")
//    public void user_quits() {
//        Driver.closeDriver();
//    }

    @Then("user logs in with {string} username and {string} password")
    public void user_logs_in_with_username_and_password(String string, String string2) {
        pages.loginPage().login(string, string2);
    }

    @Then("user verifies that {string} warning message is displayed")
    public void user_verifies_that_warning_message_is_displayed(String string) {
        Assert.assertEquals(string, pages.loginPage().getErrorMessage());
    }

    @Then("user logs in as a driver")
    public void user_logs_in_as_a_driver() {
        String username = ConfigurationReader.getProperty("driverusername");
        String password = ConfigurationReader.getProperty("driverpassword");
        pages.loginPage().login(username, password);
    }

    @When("user logs in as a {string}")
    public void user_logs_in_as_a(String role) {
        pages.loginPage().login(role);
    }

    @Given("user logs in with following credentials")
    public void user_logs_in_with_following_credentials(Map<String, String> values) {
        pages.loginPage().login(values.get("username"),values.get("password"));
    }
}
