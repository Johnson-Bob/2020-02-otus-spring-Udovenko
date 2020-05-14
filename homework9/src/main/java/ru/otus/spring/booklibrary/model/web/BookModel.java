package ru.otus.spring.booklibrary.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookModel {
    private String id;
    private String bookTitle;
    private String genre;
    private Set<AuthorModel> authors;
}
