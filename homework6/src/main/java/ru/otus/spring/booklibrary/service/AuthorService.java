package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.Collection;
import java.util.Set;

public interface AuthorService {
    AuthorDto findOrSaveAuthor(AuthorDto dto);
    Set<AuthorDto> getAllAuthors();
    Set<AuthorDto> convertToSetDto(Collection<Author> authors);
}
