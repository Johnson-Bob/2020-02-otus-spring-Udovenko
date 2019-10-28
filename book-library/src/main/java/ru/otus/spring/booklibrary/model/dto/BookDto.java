package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

@Getter
@Setter
@Builder
public class BookDto {
    private String bookName;
    private Genre genre;
    private List<Author> authors;
}
