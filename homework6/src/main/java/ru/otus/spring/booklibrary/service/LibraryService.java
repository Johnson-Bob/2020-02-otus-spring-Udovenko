package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;

import java.util.List;
import java.util.Map;

public interface LibraryService {
    BookDto addBookToLibrary(BookDto book);
    void deleteBookFromLibrary(BookDto bookDto);
    List<BookDto> getAllBooksFromLibrary();
    List<BookDto> findBookByName(String name);
    List<GenreDto> getAllGenres();
    Map<Long, CommentDto> getAllBookComments(BookDto bookDto);
}
