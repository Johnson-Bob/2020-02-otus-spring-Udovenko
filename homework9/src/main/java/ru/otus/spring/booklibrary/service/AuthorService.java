package ru.otus.spring.booklibrary.service;

import java.util.Set;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;

public interface AuthorService {
  AuthorDto findOrSaveAuthor(AuthorDto dto);

  Set<AuthorDto> getAllAuthors();
}
