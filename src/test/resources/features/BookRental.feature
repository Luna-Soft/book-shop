Feature: Book rental

  #Should add book rental
  #Should throw exception when user try to add the same book rental twice
  #Should add many books rental by one user
  #Should add many books rental by many users
  #Should throw exception when user try to add book rental without book id
  #Should throw exception when user try to add book rental without user id
  #Should update book rental when user try to return book on time
  #Should update book rental when user try to extend book first time
  #Should update book rental when user try to extend book second time
  #Should throw exception when user try to extend book third time
  #Should delete book rental
  #Should throw exception when user try to delete the same book rental twice


  Background:
    Given clean database
    Given test users in database

  Scenario: Should add book rental
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null


  Scenario: Should throw exception when user try to add the same book rental twice
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #try to post the same book rental twice
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 409


  Scenario: Should add many books rental by one user
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
    #post second book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Chamber of Secrets",
    "ISBN": "74865-754-556-1334-111",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 2
    #post first book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #post second book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 2
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 2
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #get all books rentals and check if properties ale correct
    And I request "GET api/rest/v1/booksRentals"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [0].user_id should be equal to: 1
    And Property [0].book_id should be equal to: 1
    And Property [0].extension should be equal to: 0
    And Property [0].charge should be null
    And Property [0].initial_date should be equal to: 2021-03-31
    And Property [0].expected_return_date should be equal to: 2021-04-30
    And Property [0].return_date should be null
    And Property [1].id should be equal to: 2
    And Property [1].user_id should be equal to: 1
    And Property [1].book_id should be equal to: 2
    And Property [1].extension should be equal to: 0
    And Property [1].charge should be null
    And Property [1].initial_date should be equal to: 2021-03-31
    And Property [1].expected_return_date should be equal to: 2021-04-30
    And Property [1].return_date should be null


  Scenario: Should add many books rental by many users
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
    #post second book and check if status code is correct
    And I have the json payload:
    """
    {
    "title": "Harry potter and the Chamber of Secrets",
    "ISBN": "74865-754-556-1334-111",
    "genres": [1],
    "authors": [1],
    "publishing_house": 1,
    "description": "Book about young wizard",
    "price": 15
    }
    """
    And I request "POST api/rest/v1/book"
    And The response status code should be 201
    And Property id should be equal to: 2
    #authenticate user2
    Given I have the json payload:
    """
    {
    "username": "user2",
    "password": "pwd2"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post first book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 2,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get first book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 2
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #authenticate user3
    Given I have the json payload:
    """
    {
    "username": "user3",
    "password": "pwd3"
    }
    """
    And I request "POST /authenticate"
    And The response status code should be 200
    And Get authorization token from response and put it to header
    #post second book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 3,
    "book_id": 2
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 2
    #get second book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/2"
    And The response status code should be 200
    And Property id should be equal to: 2
    And Property user_id should be equal to: 3
    And Property book_id should be equal to: 2
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #get all books rentals and check if properties ale correct
    And I request "GET api/rest/v1/booksRentals"
    And The response status code should be 200
    And Property [0].id should be equal to: 1
    And Property [0].user_id should be equal to: 2
    And Property [0].book_id should be equal to: 1
    And Property [0].extension should be equal to: 0
    And Property [0].charge should be null
    And Property [0].initial_date should be equal to: 2021-03-31
    And Property [0].expected_return_date should be equal to: 2021-04-30
    And Property [0].return_date should be null
    And Property [1].id should be equal to: 2
    And Property [1].user_id should be equal to: 3
    And Property [1].book_id should be equal to: 2
    And Property [1].extension should be equal to: 0
    And Property [1].charge should be null
    And Property [1].initial_date should be equal to: 2021-03-31
    And Property [1].expected_return_date should be equal to: 2021-04-30
    And Property [1].return_date should be null


  Scenario: Should throw exception when user try to add book rental without book id
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And Error should occured with status 400 and message contain: must not be null


  Scenario: Should throw exception when user try to add book rental without user id
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And Error should occured with status 400 and message contain: must not be null


  Scenario: Should update book rental when user try to return book
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #update book rental when user try to return book
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/returned"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-30
    And Property expected_return_date should be equal to: 2021-04-29
    And Property return_date should be equal to: 2021-03-31


  Scenario: Should update book rental when user try to extend book first time
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #update book rental when user try to extend book
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 1
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-30
    And Property expected_return_date should be equal to: 2021-05-29
    And Property return_date should be null

  Scenario: Should update book rental when user try to extend book second time
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #update book rental when user try to extend book first time
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 1
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-30
    And Property expected_return_date should be equal to: 2021-05-29
    And Property return_date should be null
    #update book rental when user try to extend book second time
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 2
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-29
    And Property expected_return_date should be equal to: 2021-06-27
    And Property return_date should be null


  Scenario: Should throw exception when user try to extend book third time
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #update book rental when user try to extend book first time
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 1
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-30
    And Property expected_return_date should be equal to: 2021-05-29
    And Property return_date should be null
    #update book rental when user try to extend book second time
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And The response status code should be 200
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 2
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-29
    And Property expected_return_date should be equal to: 2021-06-27
    And Property return_date should be null
    #try to extend book third time
    And I have the json payload:
    """
    {
    "book_id": 1
    }
    """
    And I request "PUT api/rest/v1/booksRental/1/extend"
    And Error should occured with status 403 and message contain: You have just extended book twice!


  Scenario: Should delete book rental
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #delete book rental
    And I request "DELETE api/rest/v1/booksRental/1"
    And The response status code should be 204
    #get book rental to check if status code is correct
    And I request "GET api/rest/v1/booksRental/1"
    And Error should occured with status 404 and message contain: does not exist


  Scenario: Should throw exception when user try to delete the same book rental twice
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
    #post book rental and check if status code is correct
    And I have the json payload:
    """
    {
    "user_id": 1,
    "book_id": 1
    }
    """
    And I request "POST api/rest/v1/booksRental"
    And The response status code should be 201
    And Property id should be equal to: 1
    #get book rental to check if properties are correct
    And I request "GET api/rest/v1/booksRental/1"
    And The response status code should be 200
    And Property id should be equal to: 1
    And Property user_id should be equal to: 1
    And Property book_id should be equal to: 1
    And Property extension should be equal to: 0
    And Property charge should be null
    And Property initial_date should be equal to: 2021-03-31
    And Property expected_return_date should be equal to: 2021-04-30
    And Property return_date should be null
    #delete book rental
    And I request "DELETE api/rest/v1/booksRental/1"
    And The response status code should be 204
    #get book rental to check if status code is correct
    And I request "GET api/rest/v1/booksRental/1"
    And Error should occured with status 404 and message contain: does not exist
    #try to delete the same book rental twice
    And I request "DELETE api/rest/v1/booksRental/1"
    And Error should occured with status 404 and message contain: does not exist
