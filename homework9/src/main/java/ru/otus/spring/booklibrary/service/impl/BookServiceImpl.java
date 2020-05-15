package ru.otus.spring.booklibrary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.service.AuthorService;
import ru.otus.spring.booklibrary.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toAuthorSet;
import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toBookDto;
import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.toListBookDto;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorService authorService;

    @Override
    @Transactional
    public BookDto addBookToLibrary(BookDto bookDto) {
        final Set<Author> authors = findOrSaveAuthors(bookDto.getAuthors());
        final Book newBook = new Book(null, bookDto.getBookTitle(), bookDto.getGenre(), authors, 0L);
        return EntityDtoConverter.toBookDto(bookDao.save(newBook));
    }

    @Override
    @Transactional
    public void deleteBookFromLibrary(BookDto bookDto) {
        bookDao.deleteById(bookDto.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findBookByName(String name) {
        return toListBookDto(bookDao.findByTitle(name));
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        final Set<Author>  authors = toAuthorSet(bookDto.getAuthors());
        final Book book = bookDao.findById(bookDto.getId()).orElseThrow();

        book.setTitle(bookDto.getBookTitle());
        book.setGenre(bookDto.getGenre());
        book.setAuthors(authors);

        final Book savedBook = bookDao.save(book);
        return toBookDto(savedBook);
    }

    private Set<Author> findOrSaveAuthors(Set<AuthorDto> authors) {
        return authors.stream()
                .map(authorService::findOrSaveAuthor)
                .map(dto -> new Author(dto.getId(), dto.getFirstName(), dto.getLastName()))
                .collect(Collectors.toSet());
    }
}
