package com.alura.Literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String nombre;
    private Integer añoNacimiento;
    private Integer añoFallecimiento;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books= new ArrayList<>();

    public Author() {}

    public Author(AuthorData authorData) {
        this.nombre = authorData.nombre();
        this.añoNacimiento = authorData.añoNacimiento();
        this.añoFallecimiento = authorData.añoFallecimiento();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(Integer añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public Integer getAñoFallecimiento() {
        return añoFallecimiento;
    }

    public void setAñoFallecimiento(Integer añoFallecimiento) {
        this.añoFallecimiento = añoFallecimiento;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author: " +
                 nombre ;
    }
}
