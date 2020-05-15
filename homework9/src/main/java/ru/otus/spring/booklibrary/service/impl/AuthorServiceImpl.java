package ru.otus.spring.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.service.AuthorService;

import java.util.Optional;
import java.util.Set;

import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toAuthor;
import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toAuthorDto;
import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toSetAuthorDto;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    @Transactional
    public AuthorDto findOrSaveAuthor(AuthorDto dto) {
        final Optional<Author> optionalAuthor = authorDao.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
        final Author author = optionalAuthor.orElseGet(() -> authorDao.save(toAuthor(dto)));

        return toAuthorDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<AuthorDto> getAllAuthors() {
        return toSetAuthorDto(authorDao.findAll());
    }
}
