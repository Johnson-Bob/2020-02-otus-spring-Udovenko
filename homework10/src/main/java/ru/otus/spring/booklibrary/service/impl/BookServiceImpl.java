package ru.otus.spring.booklibrary.service.impl;

import static ru.otus.spring.booklibrary.service.impl.EntityDtoConverter.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.service.AuthorService;
import ru.otus.spring.booklibrary.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookDao bookDao;
  private final AuthorService authorService;

  @Override
  public BookDto addBookToLibrary(BookDto bookDto) {
    final Set<Author> authors = findOrSaveAuthors(bookDto.getAuthors());
    final Book newBook = new Book(null, bookDto.getBookTitle(), bookDto.getGenre(), authors, null);
    return EntityDtoConverter.toBookDto(bookDao.save(newBook));
  }

  @Override
  public void deleteBookFromLibrary(BookDto bookDto) {
    bookDao.deleteById(bookDto.getId());
  }

  @Override
  public List<BookDto> findBookByName(String name) {
    return toListBookDto(bookDao.findByTitle(name));
  }

  @Override
  public BookDto updateBook(BookDto bookDto) {
    final Set<Author> authors = toAuthorSet(bookDto.getAuthors());
    final Book book = bookDao.findById(bookDto.getId()).orElseThrow();

    book.setTitle(bookDto.getBookTitle());
    book.setGenre(bookDto.getGenre());
    book.setAuthors(authors);

    final Book savedBook = bookDao.save(book);
    return toBookDto(savedBook);
  }

  private Set<Author> findOrSaveAuthors(Set<AuthorDto> authors) {
    return authors.stream().map(authorService::findOrSaveAuthor)
      .map(dto -> new Author(dto.getId(), dto.getFirstName(), dto.getLastName()))
      .collect(Collectors.toSet());
  }
}
