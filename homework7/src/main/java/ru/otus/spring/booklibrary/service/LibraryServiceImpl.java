package ru.otus.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.otus.spring.booklibrary.service.DtoConverter.convertToListBookDto;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final GenreDao genreDao;

    @Override
    @Transactional
    public BookDto addBookToLibrary(BookDto bookDto) {
        Book newBook = new Book(null, bookDto.getBookName(), bookDto.getGenre(), bookDto.getAuthors(), Collections.emptyList());
        return DtoConverter.convertToBookDto(bookDao.save(newBook));
    }

    @Override
    @Transactional
    public void deleteBookFromLibrary(BookDto bookDto) {
        bookDao.deleteById(bookDto.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooksFromLibrary() {
        return convertToListBookDto(bookDao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findBookByName(String name) {
        return convertToListBookDto(bookDao.findByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return StreamSupport.stream(genreDao.findAll().spliterator(), false)
                .map(g -> GenreDto.builder().id(g.getId()).genreName(g.getGenreName()).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, CommentDto> getAllBookComments(BookDto bookDto) {
        return bookDao.findById(bookDto.getId())
                .map(Book::getComments)
                .orElse(Collections.emptyList())
                .stream()
                .map(DtoConverter::convertToCommentDto)
                .collect(Collectors.toMap(CommentDto::getId, Function.identity()));
    }
}
