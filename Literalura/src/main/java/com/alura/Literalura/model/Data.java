package com.alura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// This annotation ensures that any unknown fields in the JSON response are ignored
@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(@JsonAlias("results") List<BookData> results // The "results" field from the JSON response
) {
}
