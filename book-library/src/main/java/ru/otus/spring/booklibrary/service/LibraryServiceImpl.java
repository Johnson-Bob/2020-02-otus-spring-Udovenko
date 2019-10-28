package ru.otus.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book addBookToLibrary(BookDto bookDto) {
        Book book = new Book(0, bookDto.getBookName(), bookDto.getGenre(), bookDto.getAuthors());;
        long bookId = bookDao.create(book);
        book.setId(bookId);
        return book;
    }

    @Override
    public boolean deleteBookFromLibrary(Book book) {
        return bookDao.delete(book.getId()) > 0;
    }

    @Override
    public List<Book> getAllBooksFromLibrary() {
        return bookDao.getAll();
    }

    @Override
    public List<Book> findBookByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }
}
