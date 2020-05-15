package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;

import java.util.List;

public interface BookService {
    BookDto addBookToLibrary(BookDto book);
    void deleteBookFromLibrary(BookDto bookDto);
    List<BookDto> findBookByName(String name);
    BookDto updateBook(BookDto bookDto);
}
