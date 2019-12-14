Feature: Employee REST APO requests

  @ApiPost
  Scenario: Post an employee method test

    Given Content type and Accept type is JSON
    When I post a new Employee with "random" id
    Then Status code is 201
    And Request json and response json should contain employee info
    When I sent a get request with "random" id
    Then Status code is 200
    And Employee json response data should match the posted data

  Scenario: POst an employee and verify UI
    Given Content type and Accept type is JSON
    When I post a new Employee with "random" id
    Then Status code is 201
    And Request json and response json should contain employee info
    And I am on department page
    Then I search for employee with "random" id
    Then UI search results much match API post employee data
