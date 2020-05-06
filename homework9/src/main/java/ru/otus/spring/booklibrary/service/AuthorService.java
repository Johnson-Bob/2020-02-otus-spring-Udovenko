package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;

import java.util.Set;

public interface AuthorService {
    AuthorDto findOrSaveAuthor(AuthorDto dto);
    Set<AuthorDto> getAllAuthors();
}
