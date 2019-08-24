package com.vytrack.step_definitions;

import com.vytrack.utilities.ConfigurationReader;
import com.vytrack.utilities.Driver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

public class Hook {

    //default hook that runs for every scenario
    //we should put some annotation to avoid it running for every test
    @Before//(order=2) //this should come from cucumber.api.java
    public void setup(Scenario scenario){
        System.out.println(scenario.getSourceTagNames());
        System.out.println(scenario.getName());
        System.out.println("BEFORE");
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Driver.getDriver().get(ConfigurationReader.getProperty("url"+ConfigurationReader.getProperty("environment")));
    }
    @After//(order=2) //this should also be from cucumber
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            //TakeScreenshot is an interface, we need to cast our driver
            TakesScreenshot takesScreenshot = (TakesScreenshot) Driver.getDriver();
            byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            //this will attach screenshot into report
            scenario.embed(image, "image/png");
        }
        Driver.closeDriver();
        System.out.println("AFTER");
    }









//    //this hook will work only for scenarios with a tag @storemanager
//    //also it will run before default hook, because of priority
//    @Before(value="@storemanager", order=1)
//    public void setupForStoreManager(Scenario scenario){
//        System.out.println("BEFORE FOR STORE MANAGER");
//    }
//
//
//    @After(value="@storemanager", order=1)
//    public void tearDownForStoreManager(){
//        System.out.println("AFTER FOR STORE MANAGER");
//    }
}
