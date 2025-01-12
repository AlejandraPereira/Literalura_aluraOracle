package com.alura.Literalura.repository;

import com.alura.Literalura.model.Author;
import com.alura.Literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNombre(String nombre);

    @Query("SELECT a FROM Author a WHERE a.añoFallecimiento != :inputYear")
    List<Author> findAuthorsAliveInYear(@Param("inputYear") int inputYear);

}
