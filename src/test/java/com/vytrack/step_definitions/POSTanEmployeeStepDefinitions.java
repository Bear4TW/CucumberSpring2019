package com.vytrack.step_definitions;

import com.vytrack.utilities.ConfigurationReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class POSTanEmployeeStepDefinitions {

    //we create an object to store our request
    RequestSpecification request;
    int employeeId;
    Response response;
    String url = ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/";
    Map reqEmployee;

    @Given("Content type and Accept type is JSON")
    public void content_type_and_Accept_type_is_JSON() {
        request = given().accept(ContentType.JSON)
        .and().contentType(ContentType.JSON);
    }

    @When("I post a new Employee with {string} id")
    public void i_post_a_new_Employee_with_id(String id) {
        if(id.equals("random")){
            employeeId = new Random().nextInt(99999);
        }else{
            employeeId = Integer.parseInt(id);
        }

        reqEmployee = new HashMap();
        reqEmployee.put("employee_id", employeeId);
        reqEmployee.put("first_name", "POSTNAME");
        reqEmployee.put("last_name", "POSTLNAME");
        reqEmployee.put("email", "EM"+employeeId);
        reqEmployee.put("phone_number", "202.123.4567");
        reqEmployee.put("hire_date", "2018-04-24T07:25:00Z");
        reqEmployee.put("job_id", "IT_PROG");
        reqEmployee.put("salary", 7000);
//        reqEmployee.put("commission_pct", null);
//        reqEmployee.put("manager_id", null);
        reqEmployee.put("department_id", 90);

        response = request.body(reqEmployee).when().post(url);
    }

    @Then("Status code is {int}")
    public void status_code_is(int statusCode) {
        Assert.assertEquals(response.statusCode(),statusCode);
    }

    @Then("Request json and response json should contain employee info")
    public void request_json_and_response_json_should_contain_employee_info() {
        Map postResEmployee = response.body().as(Map.class);
        for(Object key : reqEmployee.keySet()){
            System.out.println(postResEmployee.get(key)+" <> "+reqEmployee.get(key));
            Assert.assertEquals(postResEmployee.get(key), reqEmployee.get(key));
        }
    }

    @When("I sent a get request with {string} id")
    public void i_sent_a_get_request_with_id(String id) {
        if(!id.equals("random")){
            employeeId = Integer.parseInt(id);
        }
        response = given().accept(ContentType.JSON).when().get(url+employeeId);
    }

    @Then("Employee json response data should match the posted data")
    public void employee_json_response_data_should_match_the_posted_data() {
        Map getResMap = response.body().as(Map.class);

        for(Object key : reqEmployee.keySet()){
            System.out.println(key+" : "+reqEmployee.get(key)+" <> "+getResMap.get(key));
            Assert.assertEquals(getResMap.get(key),reqEmployee.get(key));
        }
    }

}
