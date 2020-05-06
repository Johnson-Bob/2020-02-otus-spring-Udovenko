package ru.otus.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.booklibrary.service.DtoConverter.convertToListBookDto;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final GenreDao genreDao;

    @Override
    public BookDto addBookToLibrary(BookDto bookDto) {
        Book newBook = new Book(null, bookDto.getBookTitle(), bookDto.getGenre(), bookDto.getAuthors());
        return DtoConverter.convertToBookDto(bookDao.save(newBook));
    }

    @Override
    public void deleteBookFromLibrary(BookDto bookDto) {
        bookDao.deleteById(bookDto.getId());
    }

    @Override
    public List<BookDto> getAllBooksFromLibrary() {
        return convertToListBookDto(bookDao.findAll());
    }

    @Override
    public List<BookDto> findBookByName(String name) {
        return convertToListBookDto(bookDao.findByTitle(name));
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreDao.findAll().stream()
                .map(g -> new GenreDto(g.getGenreName()))
                .collect(Collectors.toList());
    }
}
