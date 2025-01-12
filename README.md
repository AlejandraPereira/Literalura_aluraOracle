Literalura
------------------------------------------------------------------------------
-------------------------------------------------------------------------------
Literalura is an application that allows you to manage and explore books and authors using data obtained from the Gutendex API. It provides functionalities to search for books, register authors, list existing records, and query books by different criteria.



-------------------------------------------------------------------------------
Features
-------------------------------------------------------------------------------

Search books by title: Query books in the Gutendex API and automatically log the data into the database.
List registered books: Display all books stored in the database.

List registered authors: Lists saved authors along with their associated books.

Filter authors by year: Query authors alive in a specific year.

List books by language: Filter books by languages ​​such as Spanish, English, French, or Portuguese.



-------------------------------------------------------------------------------
Prerequisites
-------------------------------------------------------------------------------
Before running the application, make sure you have the following installed:

Java 17 or higher

Maven 3.8.1 or higher

PostgreSQL 12 or higher

-------------------------------------------------------------------------------
Instalation
-------------------------------------------------------------------------------

1.Clone the repository

      git clone <REPOSITORY_URL>

      cd literalura

2. Set the environment variables

Set the following environment variables to connect the application to your PostgreSQL database:

      Variable Description Example

      DB_HOST Database host localhost

      DB_PORT Database port 5432

      DB_NAME Database name literalura

      DB_USERNAME Database user postgres

      DB_PASSWORD Database password your_password

Alternatively, edit the src/main/resources/application.properties file:

properties:

   Copy code:

      spring.datasource.url=jdbc:postgresql://localhost:5432/literalura

      spring.datasource.username=postgres

      spring.datasource.password=your_password

3. Create the database
   
Create a database in PostgreSQL with the following command:

      sql

   Copy code:

      CREATE DATABASE literal;

4. Build the project

Run the following command to compile the project and download the necessary dependencies:


      bash

Copy code:

      mvn clean install

Usage:

      Run the application

To start the application, run:

      bash

Copy code:

      mvn spring-boot:run

-------------------------------------------------------------------------------
Main menu

When you run the application, the following interactive menu will be displayed in the console:


1 - Search for a book by title

2 - List of registered books

3 - List of registered authors

4 - List living Authors in a specific year

5 - List books by language

0 - Exit

-------------------------------------------------------------------------------
Technologies used
-------------------------------------------------------------------------------

Java 17

Spring Boot 3.3.5

Spring Data JPA

PostgreSQL (Relational database)

Gutendex API (For querying book data)

Jackson Databind (JSON data processing)

-------------------------------------------------------------------------------
Project structure
-------------------------------------------------------------------------------

plaintext

src

      ├── main
      
      │ ├── java/com/alura/literalura
      
      │ │ ├── principal/Principal.java # Main class
      
      │ │ ├── repository # JPA repositories
      
      │ │ ├── service # Services for API consumption and data transformation
      
      │ │ └── model # Model classes: Author, Book, Data
      
      │ └── resources
      
      │ ├── application.properties # Application configuration
      
      │ └── static # Static files
      
      └── test # Testing



