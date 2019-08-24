package com.vytrack.step_definitions;

import com.vytrack.utilities.Pages;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.util.List;

public class CalendarEventsStepDefinitions {

    Pages pages = new Pages();

    @Then("following table headers should be displayed")
    public void following_table_headers_should_be_displayed(List<String> values) {
        Assert.assertEquals(values, pages.calendarEventsPage().getTableHeaders());
    }
}
