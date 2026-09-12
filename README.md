# CRM Tool Backend

A backend application for a Customer Relationship Management (CRM) Tool developed using Java Spring Boot and PostgreSQL.

## Project Objective

The objective of this project is to build backend APIs for managing users, leads, contacts and deals in a CRM system.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit
- Mockito
- Postman
- Eclipse
- GitHub

## Main Features

- Create User
- Create Lead
- Create Contact
- Convert Lead to Contact
- Create Deal
- Pagination for list APIs
- Input Validation
- Global Exception Handling
- Unit Testing

## API Endpoints

### User
POST /api/v1/users

GET /api/v1/users?page=0&size=2

### Lead
POST /api/v1/leads

GET /api/v1/leads?page=0&size=2

POST /api/v1/leads/{id}/convert

### Contact
POST /api/v1/contacts

GET /api/v1/contacts?page=0&size=2

### Deal
POST /api/v1/deals

GET /api/v1/deals?page=0&size=2

## Database

PostgreSQL

Database Name: crm_db

## Testing

Unit tests are written using JUnit and Mockito.

APIs are tested using Postman.

## Pagination

List APIs support pagination.

Example:

GET /api/v1/leads?page=0&size=2

## Author

Sudarshan Rao## Pagination

List APIs support pagination.

Example:

GET /api/v1/leads?page=0&size=2

## Author

Madhusudan Rao