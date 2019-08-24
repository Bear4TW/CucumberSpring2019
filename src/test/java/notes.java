public class notes {
/*

###############################################################################################
August, 7



1 scenario = 1 test case.

Can feature be user acceptance criteria? yes

it will be several senarios in same future related to same  name (module-page)

Every scenario consists of steps. Scenario steps start from Gherkin keywords. First step always
Given.

EX:
     Scenario: Login as a store manager
        Given user is on the landing page
        Then user logs in as a store manager
        And user verifies that Dashboards page name is displayed
###############################################################################################
Not always 1 feature = 1 scenario. I would say - never (99%).

You could keep adding scenario into existing feature file.
Let's say new functionality was added to the logiin page. You don't wan't to create one more
Login.feature file. Instead, you would just add scenarios to test that new feature into existing
feature file.

Structure will be like this:
    we have a login page,
    For login page we will create a
    LoginPage.java - page class
    LoginStepDefinitions.java - step def class, that will contain logic that is behind scenarios.
    Login.feature - feature file that will contain scenarios to test login page.
###############################################################################################
EX: my project was very big

    step_definitions sub package for huge modules
    pages sub package for huge modules
    features sub forders for huge modules
###############################################################################################
But again, it's only available structure, I am just sharing structure that many people use and I used in
my project.
###############################################################################################
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
            "src/test/resources/features" //to specify where are the features
        },
        //feature contains scenarios
        //every scenario is like a test
        //where is the implementation for features
        glue = {"com/vytrack/step_definitions"},
        //dry tun - to generate step definitions automatically
        //you will see them in the console output
        dryRun = true

)
public class CukesRunner {
}



@RunWith(Cucumber.class) - annotation is coming from JUnit. It means that we gonna execute cucumber tests.
This annotation used on top of the runner.


@CucumberOptions - whatever is inside stands for configuration of cucumber tests.


features = {
            "src/test/resources/features" //to specify where are the features
        }, --> in order to specify location of feature files.

We can point on specific feature file, or we can point on group, or entire folder with features.


glue = {"com/vytrack/step_definitions"},

Glue indicates source of step definitions.
###############################################################################################
Since we have a couple of options, we need to put comma after every option.


dryRun = false --????
dryRun stands for generating step definitions for scenario steps.

When it's true, you will not run your code. Cucumber will check if all scenario steps have step
definitions. When it's false - you can actually run tests.

CuckesRunner why this name????
##################
You can have any name.

In my project we had couple runners.

FunctionalRunner. name doesn't affect on anything. Name can indicate purpose of runner.


Feature: As user I want to login under different roles

Feature - is a keyword from Gherkin

Any word that is inside double quote "" will become a parameter for step definition.

Once you perform dryRun, cucumber will generate unimplemented step definitions:

@Given("user is on the landing page")
public void user_is_on_the_landing_page() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@Then("user logs in as a store manager")
public void user_logs_in_as_a_store_manager() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@Then("user verifies that {string} page name is displayed")
public void user_verifies_that_page_name_is_displayed(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

Step definition is like a method that has step description on top.
By default you will see some text inside auto-generated step definition:

// Write code here that turns the phrase above into concrete actions
throw new cucumber.api.PendingException();

You have to delete it and instead provide a code. What code?
Code that supports test step. If it says "open website" - it should have a code that
will open website.

If you see skipped s, that means step definition doesn't have an implementation.

1 Scenario (1 skipped) - means dryRun is true;

We use dryRun=true to find mising step definitions.

##########################################
What if we have two same step definitions?
Which one to use?
How cucumber know?

You should avoid same step definitions. Instead you should reuse sam estep definitions.

#######################################################################################################
AUGUST 10. SATURDAY
#######################################################################################################

Thursday office hour - for testng framework.

Our main framework is Cucunber. Most of features that are new and not clear, we will
use them again for cucumber framework.

Agenda:
    Tags
    Hook
    Again step definition parameters
    Screenshot
    Basic cucumber report (it's not extant report, this reports will be created
    automaticaly. We just have to insert one line of code and that's it.)
###################################

Tags allow to run specific scenarios in cucumber and ignore others.
Usually we used tags to specify in which version of the application this feature was
developed.
EX:
        @1.4
        Feature: Login

Also it's good and preferable to put tag with story, that is coming from JIRA number on top of feature.
EX:
    @VYT-4121
    Feature: Login

That means we have corresponding JIRA ticket.

Also we can use tag @smoke in order to label scenarios for smoke test. In my project we had separate
feature for smoke test, so I didn't use tags to mark scenarios that belong to the smoke test.

In case of VYTrack we can lable all scenarios that use @driver, @salesmanager, and then in the runner
we can specify that we want to runn all scenarios for @driver.

We can create a couple of cucumber runners.

We can put multiple tags on top of scenario and feature.

If tag will be on top of feature so this tag willl be applicable to all scenarios.

"not @negative" - it is a new format to ignore
WARNING: Found tags option '~@negative'. Support for '~@tag' will be removed
from the next release of Cucumber-JVM. Please use 'not @tag' instead.

"~@negative" - to ignore scenario at run. ~ tilde.

Scenarios with tag @negative will be ignored.

Any tag that has tilde, will be ignored
If you want to run all scenarios except those who have @negative tag. just put ~
For example we want to run all except for new versions scenarios.

Step definitions have same name as tests steps, they are written in snake case.

assel_danabekovna_kassymbekova  snake case

EX:
    Given: login as driver
    step def:
        @Given("login as driver")
        public void login_as_driver(){
        }

###########################################
In simple words, hook runs before and after every scenario in cucumber. Very similar
to @BeforeMethod and @AfterMethod in testng.

Feature file = agile story
Scenario = test
IN cucumber, we can have certain logic/methods executable before and after every
cucumber scenario.

Hook class must be located under step definitions package.
Or, if you want to have it under different package, you need to specify inside runner
class glue for package with hook.

In my framework, we had separate package for hooks, it called testSetupAndTeardowns.
Name can be different, not only Hook.java.

There are 2 hooks:
@Before - that runs before scenario
@After - runs after scenario

These annotations are coming from cucumber, not junit or testng.

Hooks can be configured. We can make runnable hooks only for certain annotations.
EX.
    We have scenarios for @driver.
    We can make hooks customized only for those scenarios.

########################################
Hook is like a testbase

package com.vytrack.step_definitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook {

    @Before //this should come from cucumber.api.java
    public void setup(){

    }

    @After //this should also be from cucumber
    public void tearDown(){

    }
}
Wrong import can lead to wrong functionality.

To run scenarios that have tags:
tags = {"@storemanager or @negative"},

{"@storemanager or @negative"},
if you put and that means that there should be scenarios that contains
both of them at the same time

If you provide incorrect tags:
0 Scenarios
0 Steps
0m0.321s
Tha means cucumber cannot find scenarios/features with given tags.
###################################################

Except dryRun.
###################################################
What if we want to run hook only for scenarios with specific tag?
we shouldn't have a default hook, otherwise default hook will always run.

If scenario contains a tag that is matching expected tag from addition hook method,
it will run on top of default hook.

@Before("@storemanager") - means that this hook will work for scenarios with
annotation @storemanager.

##################################################

How to change priority?
 @Before(value="@storemanager", order=1)

 Why do we have default hook? Because it is common for all scenarios.
 If for some reason default hook is not suitable for some scenarios, you should
 have @tag gor that hook.

 Default hook is like water among all other drinks, means everyone can drink it.

 If you need to have special setup (example: framework is capable for
 back end, API, and all other tests, and for some tests you don't need browser
 let's say API, you can have hook only for API).

 ##########################################

 Why 2 hooks can be helpful?
 Example: we have a webapplication, just simple UI +back-end testing.
 And you have a feature that involves exteral application. You need to verify
 not only database of your application, but also DB (database) of that external
 application. You don't need connection with that DB for all scenarios, only for
 1-2 features you need a connection with DB external application. So you van create
 a hook that will establish connection with that DB, only for those specific
 scenarios. It can be inside one hook class.

##########################################

if we have more than one hook classes under step-_definitions ? are we going
to put the exact path to the specific hook class that we want to be considered?

NO, if hook is under same package. Of other hook located in different package,
you have to provide a path to that hook in the glue.
We will specify it in glue (if the hook is under step_definition no need to specify)
glue = {"com/vytrack/step_definitions"},

In my framework, we had 2 hook classes:
- only for Before hooks
- only for After hooks

###########################################

If scenario failed, how we gonna take a screenshot?

We have Scenario interface that provides information regarding scenario that
currently running.

###########################################

Any word inside double quotes "". will be treated as parameter, it allows to reuse
this step with different values.

Anything in "" will be a string parameter
Any number will be int parameter.

##########################################
August, 14
Agenda:
    Background
    Cucumber HTML report
    JSon report
    Maven surefire plugin
=======================================================
What is background in cucumber?
    If you have test steps that are common for all scenarios within feature file, we can move those
    steps into background.

Background runs test steps before every scenario, but not hook, only in particular given feature file.

If hook runs before and after every scenario regardless where the scenario is located, background runs
only for specific feature file, where background was created.

Example:

    // this background will be running as part of every scenario in this feature file

    Background:
    Given user is on the landing page

    @storemanager
    Scenario: Login as a store manager
    Then user logs in as a store manager
    And user verifies that "Dashboard" page name is displayed
    # Then user navigates to the "Activities" tab and "Calendar Events" module

    @negative
    Scenario: Verify warning message for invalid credentials
    Then user logs in with "wrong" username and "wrong" password
    And user verifies that "Invalid user name or password." warning message is displayed

    @driver
    Scenario: Login as a driver
    Then user logs in as a driver
    And user verifies that "Quick Launchpad" page name is displayed

Question: this is more beneficial when you have like 100 scenarios right?
    We should not have 100 scenarios in one feature file, too many, hard to manage and read.


Interview Question:
How do you run minor regression? How do you run only certain group of features?
Let's say all scripts for fleet.
    -tags
    -in the runner specify path to the folder for specific features only, this way you don't need to worry
     about tags.

        features = {
            "src/test/resources/features/fleet",
            "src/test/resources/features/login" - way of running 2 packages with features, minor regression
        },

Don't change step definitions.

In this way I select tests that I need to run for minor regression.
=================================================

Don't forget that this report will require json file, specify it in the runner class.

Question:


We will have a different cukesrunner for each test suite: like smoke, regression, etc.

=================================================

Don't forget that this report will require json file, specify it in the runner class.

Question:So when we run it through Jenkins, will it be available in Jenkins too?
    YES, also Jenkins has a plugin that can generate this report in the post build stage.

    Jenkins is a Ci/CD tool, we will talk about it later.

=================================================

Ths line means that report has been generated. We need to execute maven goal verify in order to get this report.
Otherwise, if you will run in from CukesRunner directly, it will not trigger a plagin that generates
report.

 --- maven-cucumber-reporting:4.8.0:generate (execution) @ OnlineSpring2019Cucumber ---

=================================================

In order to run specific scenarios from terminal use the following:
    mvn clean verify -Dcucumber.options="--tags @navigation"

=================================================

How Maven knows which CukesRunner class wil run?
*/

//    <plugin>
//                <groupId>org.apache.maven.plugins</groupId>
//                <artifactId>maven-surefire-plugin</artifactId>
//                <version>2.22.2</version>
//                <configuration>
//                    <includes>
//                        <include>**/CukesRunner.java</include>
//                    </includes>
//                    <testFailureIgnore>true</testFailureIgnore>
//                </configuration>
//            </plugin>

/*

If you want to have several build comfigurations you need to use profiles.

Build lyfecycle is a sequence of tasks that we use to build a software.





 */
}
