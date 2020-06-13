package ru.otus.spring.booklibrary.service.impl;

import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.*;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.service.AuthorService;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorDao authorDao;

  @Override
  public AuthorDto findOrSaveAuthor(AuthorDto dto) {
    final Optional<Author> optionalAuthor = authorDao.findByFirstNameAndLastName(dto.getFirstName(),
      dto.getLastName());
    final Author author = optionalAuthor.orElseGet(() -> authorDao.save(toAuthor(dto)));

    return toAuthorDto(author);
  }

  @Override
  public Set<AuthorDto> getAllAuthors() {
    return toSetAuthorDto(authorDao.findAll());
  }
}
