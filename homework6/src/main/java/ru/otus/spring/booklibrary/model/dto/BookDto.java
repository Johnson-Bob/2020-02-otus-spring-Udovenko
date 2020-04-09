package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class BookDto {
    private Long id;
    private String bookName;
    private GenreDto genreDto;
    private Set<AuthorDto> authors;

    @Override
    public String toString() {
        return "\"" + bookName + "\" " + genreDto.toString() + " "
                + authors.stream().map(AuthorDto::shortString).collect(Collectors.joining(", "));
    }

    public Set<Author> getAuthors() {
        return authors.stream()
                .map(dto -> new Author(dto.getId(), dto.getFirstName(), dto.getLastName()))
                .collect(Collectors.toSet());
    }

    public Genre getGenre() {
        return new Genre(genreDto.getId(), genreDto.getGenreName());
    }
}
