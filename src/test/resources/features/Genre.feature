Feature: Genre

  #Should add genre
  #Should add two genres by one user
  #Should throw exception when user try to add the same genre twice
  #Should throw exception when user try to add genre without name
  #Should add genres by many users
  #Should update genre
  #Should delete genre
  #Should throw exception when user try to delete the same genre twice


  Background:
    Given clean database
    Given test users in database

  Scenario: Should add genre
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property name should be equal to: horror
    Then Property id should be equal to: 1


  Scenario: Should add two genres by one user
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
    #post first genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
    #post second genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "comedy"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second genre and check if properties are correct
    And I request "GET api/rest/v1/genre/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: comedy
    #get all genres and check if properties are correct
    And I request "GET api/rest/v1/genres"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [1].id should be equal to: 2
    And Property [0].name should be equal to: horror
    Then Property [1].name should be equal to: comedy


  Scenario: Should throw exception when user try to add the same genre twice
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
    #try to post the same genre one more time
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    Then Error should occured with status 409 and message contain: already exists


  Scenario: Should throw exception when user try to add genre without name
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
    #try to add genre without name
    And I have the json payload:
    """
    {
    
    }
    """
    And I request "POST api/rest/v1/genre"
    Then Error should occured with status 400 and message contain: must not be blank


  Scenario: Should add genres by many users
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "comedy"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: comedy
    #get all genres and check if properties are correct
    And I request "GET api/rest/v1/genres"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [1].id should be equal to: 2
    And Property [0].name should be equal to: horror
    Then Property [1].name should be equal to: comedy


  Scenario: Should update genre
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
    #put genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "comedy"
    }
    """
    And I request "PUT api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    Then Property name should be equal to: comedy


  Scenario: Should delete genre
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
    #delete genre and check if status code is correct
    And I request "DELETE api/rest/v1/genre/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/genre/1"
    Then Error should occured with status 404 and message contain: does not exist


  Scenario: Should throw exception when user try to delete the same genre twice
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
    #post genre and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "horror"
    }
    """
    And I request "POST api/rest/v1/genre"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get genre to check if properties are correct
    And I request "GET api/rest/v1/genre/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: horror
    #delete genre and check if status code is correct
    And I request "DELETE api/rest/v1/genre/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/genre/1"
    And Error should occured with status 404 and message contain: does not exist
    #try to delete the same genre twice
    And I request "DELETE api/rest/v1/genre/1"
    Then Error should occured with status 404 and message contain: does not exist