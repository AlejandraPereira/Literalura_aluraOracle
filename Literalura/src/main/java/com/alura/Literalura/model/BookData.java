package com.alura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
// This annotation ensures that any unknown fields in the JSON response are ignored
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(

        // Maps the JSON field "title" to this class's title field
     @JsonAlias("title")  String titulo,

     // Maps the JSON field "authors" to this class's authors field
     @JsonAlias("authors") List<AuthorData> autor,


     // Maps the JSON field "languages" to this class's languages field
     @JsonAlias("languages") List<String> idioma,

     // Maps the JSON field "download_count" to this class's downloadCount field
     @JsonAlias("download_count") Double numeroDeDescargas
) {
}



