package com.alura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
      @JsonAlias("name") String nombre,
      @JsonAlias("birth_year") Integer añoNacimiento,
      @JsonAlias("death_year") Integer añoFallecimiento
) {
}
