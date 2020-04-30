package ru.otus.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.Optional;
import java.util.Set;

import static ru.otus.spring.booklibrary.service.DtoConverter.convertToAuthorDto;
import static ru.otus.spring.booklibrary.service.DtoConverter.convertToSetAuthorDto;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    @Transactional
    public AuthorDto findOrSaveAuthor(AuthorDto dto) {
        Optional<Author> optionalAuthor = authorDao.findByFirstAndLastNames(dto.getFirstName(), dto.getLastName());
        Author author = optionalAuthor.orElseGet(() -> authorDao.save(convertToAuthor(dto)));

        return convertToAuthorDto(author);
    }

    @Override
    @Transactional
    public Set<AuthorDto> getAllAuthors() {
        return convertToSetAuthorDto(authorDao.getAllAuthors());
    }

    private Author convertToAuthor(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFirstName(), dto.getLastName());
    }
}
