package ru.otus.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreDao genreDao;

    @Override
    @Transactional
    public BookDto addBookToLibrary(BookDto bookDto) {
        Book newBook = new Book(null, bookDto.getBookName(), bookDto.getGenre(), bookDto.getAuthors());
        return convertToDto(bookDao.save(newBook));
    }

    @Override
    @Transactional
    public void deleteBookFromLibrary(BookDto bookDto) {
        bookDao.deleteById(bookDto.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooksFromLibrary() {
        return convertToListDto(bookDao.getAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findBookByName(String name) {
        return convertToListDto(bookDao.getByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreDao.getAllGenres().stream()
                .map(g -> GenreDto.builder().id(g.getId()).genreName(g.getGenreName()).build())
                .collect(Collectors.toList());
    }

    private BookDto convertToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .bookName(book.getName())
                .genreDto(GenreDto.builder().id(book.getGenre().getId()).genreName(book.getGenre().getGenreName()).build())
                .authors(authorService.convertToSetDto(book.getAuthors()))
                .build();
    }

    private List<BookDto> convertToListDto(List<Book> books) {
        List<BookDto> result = new ArrayList<>(books.size());
        books.forEach(book -> result.add(convertToDto(book)));

        return result;
    }
}
