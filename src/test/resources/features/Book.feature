Feature: Book

  #Should add book
  #Should add two books by one user
  #Should throw exception when user try to add the same book twice
  #Should throw exception when user try to add book without title
  #Should throw exception when user try to add book without ISBN
  #Should throw exception when user try to add book without genres
  #Should throw exception when user try to add book without authors
  #Should throw exception when user try to add book without publishing house
  #Should throw exception when user try to add book without price
  #Should add books by many users
  #Should update book
  #Should delete book
  #Should throw exception when user try to delete the same book twice


  Background:
    Given clean database
    Given test users in database


  Scenario: Should add book
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    Then Property id should be equal to: 1


  Scenario: Should add two books by one user
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post first book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
    #post second book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Chamber of Secrets",
    "ISBN": "74845-754-556-1344",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 20
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second book and check if properties are correct
    And I request "GET api/rest/v1/book/2"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Chamber of Secrets
    And Property ISBN should be equal to: 74845-754-556-1344
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 20
    And Property id should be equal to: 2
    #get all books and check if properties are correct
    And I request "GET api/rest/v1/books"
    And The response status code should be 200
    And Property [0].title should be equal to: Harry potter and the Philosopher's Stone
    And Property [0].ISBN should be equal to: 74865-754-556-1334
    And Property [0].description should be equal to: Book about young wizard
    And Property [0].price should be equal to: 15
    And Property [0].id should be equal to: 1
    And Property [1].title should be equal to: Harry potter and the Chamber of Secrets
    And Property [1].ISBN should be equal to: 74845-754-556-1344
    And Property [1].description should be equal to: Book about young wizard
    And Property [1].price should be equal to: 20
    Then Property [1].id should be equal to: 2



  Scenario: Should throw exception when user try to add the same book twice
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
    #try to post the same book one more time
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 409 and message contain: already exists


  Scenario: Should throw exception when user try to add book without title
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without title
    And I have the json payload:
    """
    {
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be blank


  Scenario: Should throw exception when user try to add book without ISBN
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without ISBN
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be blank


  Scenario: Should throw exception when user try to add book without genres
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without genres
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be empty


  Scenario: Should throw exception when user try to add book without authors
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without authors
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be empty


  Scenario: Should throw exception when user try to add book without publishing house
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without publishing house
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be null


  Scenario: Should throw exception when user try to add book without price
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #try to add book without price
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard"
    }
    """
    And I request "POST api/rest/v1/book"
    Then Error should occured with status 400 and message contain: must not be null


  Scenario: Should add books by many users
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
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
    #post book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Chamber of Secrets",
    "ISBN": "74845-754-556-1344",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 20
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/2"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Chamber of Secrets
    And Property ISBN should be equal to: 74845-754-556-1344
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 20
    And Property id should be equal to: 2
    #get all books and check if properties are correct
    And I request "GET api/rest/v1/books"
    And The response status code should be 200
    And Property [0].title should be equal to: Harry potter and the Philosopher's Stone
    And Property [0].ISBN should be equal to: 74865-754-556-1334
    And Property [0].description should be equal to: Book about young wizard
    And Property [0].price should be equal to: 15
    And Property [0].id should be equal to: 1
    And Property [1].title should be equal to: Harry potter and the Chamber of Secrets
    And Property [1].ISBN should be equal to: 74845-754-556-1344
    And Property [1].description should be equal to: Book about young wizard
    And Property [1].price should be equal to: 20
    Then Property [1].id should be equal to: 2


  Scenario: Should update book
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
    #put book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334-111",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 20
    }
    """
    And I request "PUT api/rest/v1/book/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334-111
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 20
    Then Property id should be equal to: 1


  Scenario: Should delete book
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
    #delete book and check if status code is correct
    And I request "DELETE api/rest/v1/book/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/book/1"
    Then Error should occured with status 404 and message contain: does not exist


  Scenario: Should throw exception when user try to delete the same book twice
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
    #post publishing house and check if status code is correct
    And I have the json payload:
    """
    {
    "name" : "Nowa Era"
    }
    """
    And I request "POST api/rest/v1/publishingHouse"
    And The response status code should be 201
    #post book and check if status code is correct
    And I have the json payload:
     """
    {
    "title": "Harry potter and the Philosopher's Stone",
    "ISBN": "74865-754-556-1334",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book to check if properties are correct
    And I request "GET api/rest/v1/book/1"
    And The response status code should be 200
    And Property title should be equal to: Harry potter and the Philosopher's Stone
    And Property ISBN should be equal to: 74865-754-556-1334
    And Property description should be equal to: Book about young wizard
    And Property price should be equal to: 15
    And Property id should be equal to: 1
    #delete book and check if status code is correct
    And I request "DELETE api/rest/v1/book/1"
    And The response status code should be 204
    And I request "GET api/rest/v1/book/1"
    And Error should occured with status 404 and message contain: does not exist
    #try to delete the same book twice
    And I request "DELETE api/rest/v1/book/1"
    Then Error should occured with status 404 and message contain: does not exist

