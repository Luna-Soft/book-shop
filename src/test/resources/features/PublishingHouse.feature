Feature: Publishing house

  #Should add publishing house
  #Should add two publishing houses by one user
  #Should throw exception when user try to add the same publishing house twice
  #Should throw exception when user try to add publishing house without name
  #Should add publishing houses by many users
  #Should update publishing house
  #Should delete publishing house
  #Should throw exception when user try to delete the same publishing house twice


  Background:
    Given clean database
    Given test users in database

  Scenario: Should add publishing house
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property name should be equal to: Nowa Era
    Then Property id should be equal to: 1


  Scenario: Should add two publishing houses by one user
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post first publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first publishingHouse to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #post second publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nortom"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second publishin house and check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: Nortom
    #get all publishing houses and check if properties are correct
    And I request "GET api/rest/v1/publishingHouses"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [1].id should be equal to: 2
    And Property [0].name should be equal to: Nowa Era
    Then Property [1].name should be equal to: Nortom


  Scenario: Should throw exception when user try to add the same publishing house twice
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #try to post the same publishing house one more time
    And I have the json payload:
    """
    {
    "name": "Nowa era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    Then Error should occured with status 409 and message contain: already exists


  Scenario: Should throw exception when user try to add publishing house without name
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #try to add publishing house without name
    And I have the json payload:
    """
    {

    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    Then Error should occured with status 400 and message contain: must not be blank


  Scenario: Should add publishing houses by many users
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #authenticate user2
    And I have the json payload:
    """
    {
    "username": "user2",
    "password": "pwd2"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing housde and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nortom"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: Nortom
    #get all publishing houses and check if properties are correct
    And I request "GET api/rest/v1/publishingHouses"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [1].id should be equal to: 2
    And Property [0].name should be equal to: Nowa Era
    Then Property [1].name should be equal to: Nortom


  Scenario: Should update publishing house
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #put publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nortom"
    }
    """
    And I request "PUT api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    Then Property name should be equal to: Nortom


  Scenario: Should delete publishing house
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #delete publishing house and check if status code is correct
    And I request "DELETE api/rest/v1/publishingHouse/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/publishingHouse/1"
    Then Error should occured with status 404 and message contain: does not exist

  Scenario: Should throw exception when user try to delete the same publishing house twice
    #authenticate user1
    Given I have the json payload:
    """
    {
    "username": "user1",
    "password": "pwd1"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get publishing house to check if properties are correct
    And I request "GET api/rest/v1/publishingHouse/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Nowa Era
    #delete publishing house and check if status code is correct
    And I request "DELETE api/rest/v1/publishingHouse/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/publishingHouse/1"
    And Error should occured with status 404 and message contain: does not exist
    #try to delete the same publishing house twice
    And I request "DELETE api/rest/v1/publishingHouse/1"
    Then Error should occured with status 404 and message contain: does not exist