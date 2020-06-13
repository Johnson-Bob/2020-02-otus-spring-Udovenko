package ru.otus.spring.booklibrary.service;

import java.util.List;

import ru.otus.spring.booklibrary.model.dto.BookDto;

public interface BookService {
  BookDto addBookToLibrary(BookDto book);

  void deleteBookFromLibrary(BookDto bookDto);

  List<BookDto> findBookByName(String name);

  BookDto updateBook(BookDto bookDto);
}
