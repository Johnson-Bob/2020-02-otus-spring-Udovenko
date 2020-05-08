package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class BookDto {
    private String id;
    private String bookTitle;
    private String genre;
    private Set<AuthorDto> authors;

    public Set<Author> getAuthors() {
        return authors.stream()
                .map(dto -> new Author(dto.getId(), dto.getFirstName(), dto.getLastName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "\"" + bookTitle + "\" " + genre + " "
                + authors.stream().map(AuthorDto::shortString).collect(Collectors.joining(", "));
    }
}
