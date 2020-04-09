package ru.otus.spring.booklibrary.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.BookAuthor;
import ru.otus.spring.booklibrary.model.entity.Genre;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    private final AuthorDaoJdbc authorDao;
    private SimpleJdbcInsert simpleInsert;

    @PostConstruct
    private void init() {
        simpleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("book").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = jdbcTemplate.query("SELECT b.id, b.name, g.id genre_id, g.genre_name " +
                "FROM book b LEFT JOIN genre g ON b.genre_id = g.id;", new BookRowMapper());
        mergeBooksAndAuthors(books);

        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        Book book = jdbcTemplate.queryForObject("SELECT b.id, b.name, g.id genre_id, g.genre_name " +
                "FROM book b LEFT JOIN genre g ON b.genre_id = g.id " +
                "WHERE b.id = ?;", new Long[]{id}, new BookRowMapper());
        book.setAuthors(authorDao.getByBookId(id));

        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getByName(String name) {
        List<Book> books = jdbcTemplate.query("SELECT b.id, b.name, g.id genre_id, g.genre_name " +
                "FROM book b LEFT JOIN genre g ON b.genre_id = g.id " +
                "WHERE b.name = ?;", new String[] {name}, new BookRowMapper());
        mergeBooksAndAuthors(books);

        return books;
    }

    @Override
    @Transactional
    public long create(Book book) {
        Map<String, Object> params = Map.of("genre_id", book.getGenre().getId(), "name", book.getName());
        long bookId = simpleInsert.executeAndReturnKey(params).longValue();
        book.getAuthors().forEach(author -> {
            jdbcTemplate.update("INSERT INTO book_author (book_id, author_id) VALUES (?, ?)", bookId, author.getId());
        });

        return bookId;
    }

    @Override
    @Transactional
    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id = ?;", new Long[]{id});
    }

    private List<BookAuthor> getAllRelations() {
        return jdbcTemplate.query("SELECT book_id, author_id FROM book_author",
                ((rs, i) -> new BookAuthor(rs.getLong("book_id"), rs.getLong("author_id"))));
    }

    private void mergeBooksAndAuthors(List<Book> bookList) {
        Map<Long, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        Map<Long, Author> authorMap = authorDao.getAllUsed().stream().collect(Collectors.toMap(Author::getId, Function.identity()));

        getAllRelations().forEach(r -> {
            if (bookMap.containsKey(r.getBookId()) && authorMap.containsKey(r.getAuthorId())) {
                bookMap.get(r.getBookId()).getAuthors().add(authorMap.get(r.getAuthorId()));
            }
        });

    }

    private class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("name"),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_name")), new ArrayList<>());
        }
    }
}
