package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

public interface LibraryService {
    Book addBookToLibrary(BookDto book);
    boolean deleteBookFromLibrary(Book book);
    List<Book> getAllBooksFromLibrary();
    List<Book> findBookByName(String name);
    List<Genre> getAllGenres();
    List<Author> getAllAuthors();
}
