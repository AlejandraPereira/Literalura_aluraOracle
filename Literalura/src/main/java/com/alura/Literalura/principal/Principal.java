package com.alura.Literalura.principal;
import com.alura.Literalura.model.*;
import com.alura.Literalura.repository.IBookRepository;
import com.alura.Literalura.repository.IAuthorRepository;
import com.alura.Literalura.service.ConsumeAPI;
import com.alura.Literalura.service.ConvertingData;
import jakarta.transaction.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumeAPI consumoApi = new ConsumeAPI();
    private ConvertingData conver = new ConvertingData();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    private IBookRepository bookRepository;
    private IAuthorRepository authorRepository;

    public Principal(IBookRepository bookRepository, IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    public void showMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Search book by title 
                    2 - List registered books
                    3 - List registered Authors
                    4 - List living Authors in a specific year
                    5 - List books by language
                                  
                    0 - Exit
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    findBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listLivingAuthorsInYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }

    }

    // Method to obtain de book data from the API
    private Data obtainDataBook(String bookTitle) {
        try {
            // URL encode the book title to handle special characters
            String titleCodedBook = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);

            // Call the API with the encoded title
            var json = consumoApi.obtainData((URL_BASE + titleCodedBook));

            // Check if the API response is null or empty
            if (json == null || json.isEmpty()) {
                System.out.println("No answer from API");
                return null;
            }

            // Deserialize the JSON response into a Data object
            Data bookData = conver.obtainData(json, Data.class);

            // Check if there are no results in the API response
            if (bookData == null || bookData.results().isEmpty()) {
                System.out.println("No matches found for title: " + bookTitle);
                return null;
            }

            // Return the book data if everything is correct
            return bookData;

        } catch (Exception e) {
            // Catch any exceptions and display the error message
            System.out.println("Error to obtain data: " + e.getMessage());
        }

        // Return null in case of failure or no data
        return null;
    }



    // Method to obtain or create an author
    private Author obtainAuthor(AuthorData authorData) {
        // Check if the author exists in the database
        Optional<Author> existingAuthor = authorRepository.findByNombre(authorData.nombre());
        if (existingAuthor.isPresent()) {
            return existingAuthor.get(); // Return the existing author
        } else {
            // If the author doesn't exist, create and save a new one
            Author newAuthor = new Author();
            newAuthor.setNombre(authorData.nombre());
            newAuthor.setAñoNacimiento(authorData.añoNacimiento());
            newAuthor.setAñoFallecimiento(authorData.añoFallecimiento());
            authorRepository.save(newAuthor); // Save the new author
            return newAuthor;
        }
    }

    private void findBookByTitle() {
        System.out.println("\nPlease insert the name of the book: ");
        var bookName = teclado.nextLine();

        // Retrieve book data from the API
        Data data = obtainDataBook(bookName);

        if (data != null) {
            System.out.println("********************************");
            System.out.println("Results for the search:");
            System.out.println("********************************\n");

            // Use Stream to process and filter unique results
            data.results().stream()
                    .distinct() // Ensures that books are unique
                    .forEach(bookData -> {
                        System.out.println("Title: " + bookData.titulo());
                        System.out.println("Author: " + bookData.autor().stream()
                                .map(author -> author.nombre())
                                .collect(Collectors.toList()));
                        System.out.println("Language: " + bookData.idioma());
                        System.out.println("Download Count: " + bookData.numeroDeDescargas());
                        System.out.println("********************************");

                        // Check if the book already exists in the database
                        Optional<Book> book = bookRepository.findByTituloIgnoreCase(bookName.toLowerCase());
                        if (book.isPresent()) {
                            System.out.println("\nThe book is already in the database.");
                        } else {
                            // Create a new book and add it to the database
                            Book newBook = new Book();
                            newBook.setTitulo(bookData.titulo());
                            newBook.setLenguaje(bookData.idioma().toString());

                            // Create or retrieve authors and associate them with the book
                            List<Author> authors = new ArrayList<>();
                            for (AuthorData authorData : bookData.autor()) {
                                Author author = obtainAuthor(authorData);
                                authors.add(author);
                            }

                            // Assign authors to the book
                            newBook.setAuthors(authors);

                            // Save the new book in the database
                            bookRepository.save(newBook);
                        }
                    });
        } else {
            System.out.println("No data found for the book.");
        }
    }



    public void listRegisteredBooks() {
        List<Book> books = bookRepository.findAll(); // Find all books
        if (books.isEmpty()) {
            System.out.println("No books registered.");
        } else {
            System.out.println("********************************");
            System.out.println("List of Registered Books:");
            System.out.println("********************************\n");
            for (Book book : books) {
                book.getAuthors().size();
                System.out.println("Author: " + book.getAuthors() + ",Title: " + book.getTitulo());
            }
            System.out.println("********************************");
        }
    }

    public void listRegisteredAuthors() {
        List<Author> authors = authorRepository.findAll(); // Find all authors
        if (authors.isEmpty()) {
            System.out.println("No authors registered.");
        } else {
            System.out.println("********************************");
            System.out.println("List of Registered Authors:");
            System.out.println("********************************\n");
            for (Author author : authors) {
                System.out.println("Author: " + author.getNombre());
            }
            System.out.println("\n********************************");
        }
    }

    private void listLivingAuthorsInYear() {
        // Prompt for the year
        System.out.println("Please enter the year to find living authors:");
        int year = teclado.nextInt();  // Get year input from the user
        teclado.nextLine();  // Consume the newline character left by nextInt()

        // Logic to list authors who were alive in the specified year
        // Call the  repository  to fetch and display the authors
        List<Author> authors = authorRepository.findAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("\nNo living authors found for the year " + year);
        } else {
            System.out.println("********************************");
            System.out.println("The living actors are:");
            System.out.println("********************************\n");
            for (Author author: authors){
                System.out.println(author);
            }
            System.out.println("\n********************************");

        }
    }


    private void listBooksByLanguage() {
        System.out.println("Please enter the language to filter books:");
        System.out.println("For English: en");
        System.out.println("For Spanish: es");
        System.out.println("For French: fr");
        System.out.println("For Portuguese: pt");

        String userInput = teclado.nextLine().toLowerCase();

        // Validate the user's input
        if (!(userInput.equals("en") || userInput.equals("es") || userInput.equals("fr") || userInput.equals("pt"))) {
            System.out.println("\nInvalid language code. Please use 'en' for English, 'es' for Spanish, 'fr' for French, or 'pt' for Portuguese.");
            return; // Exit the method if the input is invalid
        }

        // Fetch books by the normalized language code
        List<Book> books = bookRepository.findByLenguaje(String.valueOf(userInput));
        if (books.isEmpty()) {
            // No books found for the specified language
            System.out.println("\nNo books found in the language: " + userInput);
        } else {
            System.out.println("********************************");
            System.out.println("The books in that lenguage are: ");
            System.out.println("********************************\n");
            // Display all books matching the specified language
            for (Book book : books) {
                System.out.println(book.getTitulo());
            }
            System.out.println("\n********************************");
        }

    }



}



