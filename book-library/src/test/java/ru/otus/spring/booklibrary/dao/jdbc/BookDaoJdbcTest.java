package ru.otus.spring.booklibrary.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.TestUtils;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.BookAuthor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with book")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class})
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc booksDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("should return all books")
    void shouldGetAll() {
        List<Book> booksFromDatabase = booksDao.getAll();

        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(2)
                .allMatch(b -> StringUtils.isNotBlank(b.getName()))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> !b.getAuthors().isEmpty());
    }

    @Test
    @DisplayName("should return correct book by id")
    void shouldGetCorrectBookById() {
//        Arrange
        Book existingBook = TestUtils.createExistingTestBook();
//        Act
        Book bookFromDatabase = booksDao.getById(existingBook.getId());

//        Assert
        TestUtils.assertTwoBooks(bookFromDatabase, existingBook);
    }

    @Test
    @DisplayName("should return correct book by book's name")
    void shouldGetBookByName() {
        //        Arrange
        Book existingBook = TestUtils.createExistingTestBook();
//        Act
        List<Book> booksFromDatabase = booksDao.getByName(existingBook.getName());

//        Assert
        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(1);
        Book bookFromDb = booksFromDatabase.get(0);
        assertThat(bookFromDb.getId()).isEqualTo(existingBook.getId());
        TestUtils.assertTwoBooks(bookFromDb, existingBook);
    }

    @Test
    @DisplayName("should return empty list when don't find book by name")
    void shouldReturnEmptyList() {
//        Arrange
        String bookName = "abracatabra";

//        Act
        List<Book> foundBooks = booksDao.getByName(bookName);

//        Assert
        assertThat(foundBooks).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should save book in database and create relation with authors")
    void shouldSaveBookInDatabase() {
//        Arrange
        Book newBook = TestUtils.createNewTestBook();

//        Act
        long newBookId = booksDao.create(newBook);
        Book newBookFromDatabase = booksDao.getById(newBookId);

//        Assert
        assertThat(newBookId).isGreaterThan(0);
        TestUtils.assertTwoBooks(newBook, newBookFromDatabase);
    }

    @Test
    @DisplayName("should delete book from database and delete relations with authors")
    void shouldDeleteBookFromDatabase() {
//        Act
        int isDelete = booksDao.delete(2);
        List<BookAuthor> remainingRelations = jdbcTemplate.query("SELECT * FROM book_author WHERE book_id = 2",
                BeanPropertyRowMapper.newInstance(BookAuthor.class));

//        Assert
        assertThat(isDelete).isGreaterThan(0);
        assertThat(remainingRelations).isEmpty();
    }
}
