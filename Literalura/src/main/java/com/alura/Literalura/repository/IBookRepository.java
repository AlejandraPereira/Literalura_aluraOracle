package com.alura.Literalura.repository;

import com.alura.Literalura.model.Book;
import com.alura.Literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTituloIgnoreCase(String titulo);

    @Query("SELECT b FROM Book b WHERE b.lenguaje LIKE %:lenguaje%")
    List<Book> findByLenguaje(@Param("lenguaje") String lenguaje);




}
