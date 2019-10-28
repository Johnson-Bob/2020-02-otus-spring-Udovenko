package ru.otus.spring.booklibrary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private long id;
    private String name;
    private Genre genre;
    private List<Author> authors;

    @Override
    public String toString() {
        return "\"" + name + "\" " + genre.toString() + " "
                + authors.stream().map(Author::shortString).collect(Collectors.joining(", "));
    }
}
