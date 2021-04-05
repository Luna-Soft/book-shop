Feature: Author

  #Should add author
  #Should add two authors by one user
  #Should throw exception when user try to add the same author twice
  #Should throw exception when user try to add author without name
  #Should throw exception when user try to add author without surname
  #Should add authors by many users
  #Should update author
  #Should delete author
  #Should throw exception when user try to delete the same author twice

  Background:
    Given clean database
    Given test users in database


  Scenario: Should add author
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property name should be equal to: Alex
    And Property surname should be equal to: Kava
    Then Property id should be equal to: 1


  Scenario: Should add two authors by one user
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
    #post first author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex-1",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex-1
    And Property surname should be equal to: Kava
    #post second author and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Alex-2",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second author and check if properties are correct
    And I request "GET api/rest/v1/author/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: Alex-2
    And Property surname should be equal to: Kava
    #get all authors and check if properties are correct
    And I request "GET api/rest/v1/authors"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [0].name should be equal to: Alex-1
    And Property [0].surname should be equal to: Kava
    And Property [1].id should be equal to: 2
    And Property [1].name should be equal to: Alex-2
    Then Property [1].surname should be equal to: Kava


  Scenario: Should throw exception when user try to add the same author twice
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex
    And Property surname should be equal to: Kava
    #try to post the same author one more time
    And I have the json payload:
    """
    {
    "name": "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    Then Error should occured with status 409 and message contain: already exists


  Scenario: Should throw exception when user try to add author without name
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
    #try to add author without name
    And I have the json payload:
    """
    {
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    Then Error should occured with status 400 and message contain: must not be blank

  Scenario: Should throw exception when user try to add author without surname
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
    #try to add author without surname
    And I have the json payload:
    """
    {
    "name": "Alex"
    }
    """
    And I request "POST api/rest/v1/author"
    Then Error should occured with status 400 and message contain: must not be blank


  Scenario: Should add authors by many users
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex-1",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex-1
    And Property surname should be equal to: Kava
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex-2",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property name should be equal to: Alex-2
    And Property surname should be equal to: Kava
    #get all authors and check if properties are correct
    And I request "GET api/rest/v1/authors"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [0].name should be equal to: Alex-1
    And Property [0].surname should be equal to: Kava
    And Property [1].id should be equal to: 2
    And Property [1].name should be equal to: Alex-2
    Then Property [1].surname should be equal to: Kava


  Scenario: Should update author
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex
    And Property surname should be equal to: Kava
    #put author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex-1",
    "surname": "Kava"
    }
    """
    And I request "PUT api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex-1
    Then Property surname should be equal to: Kava


  Scenario: Should delete author
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex
    And Property surname should be equal to: Kava
    #delete author and check if status code is correct
    And I request "DELETE api/rest/v1/author/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/author/1"
    Then Error should occured with status 404 and message contain: does not exist


  Scenario: Should throw exception when user try to delete the same author twice
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
    #post author and check if status code is correct
    And I have the json payload:
    """
    {
    "name": "Alex",
    "surname": "Kava"
    }
    """
    And I request "POST api/rest/v1/author"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get author to check if properties are correct
    And I request "GET api/rest/v1/author/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property name should be equal to: Alex
    And Property surname should be equal to: Kava
    #delete author and check if status code is correct
    And I request "DELETE api/rest/v1/author/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/author/1"
    And Error should occured with status 404 and message contain: does not exist
    #try to delete the same author twice
    And I request "DELETE api/rest/v1/author/1"
    Then Error should occured with status 404 and message contain: does not exist