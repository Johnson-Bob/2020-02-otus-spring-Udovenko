package ru.otus.spring.booklibrary.dao;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.bee.MongoBeeConfig;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with book")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoBeeConfig.class)
class BookDaoJpaTest {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private MongoTemplate mt;

    @Test
    @DisplayName("should return correct book by book's title")
    void shouldGetBookByType() {
        Book existBook = mt.findAll(Book.class).get(0);

        List<Book> booksFromDatabase = bookDao.findByTitle(existBook.getTitle());

        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(1);
        Book bookFromDb = booksFromDatabase.get(0);
        assertThat(bookFromDb.getId()).isEqualTo(existBook.getId());
        assertTwoBooks(bookFromDb, existBook);
    }

    @Test
    @DisplayName("should return empty list when don't find book by name")
    void shouldReturnEmptyList() {
        String bookName = "abracatabra";

        List<Book> foundBooks = bookDao.findByTitle(bookName);

        assertThat(foundBooks).isNotNull().isEmpty();
    }

    private void assertTwoBooks(Book testBook, Book exampleBook) {
        Iterator<Author> iterator= exampleBook.getAuthors().iterator();
        Author author1 = iterator.next();
        Author author2 = iterator.next();

        assertThat(testBook).isNotNull().isEqualToComparingOnlyGivenFields(exampleBook, "title");
        assertThat(testBook.getGenre()).isNotNull().isEqualTo(exampleBook.getGenre());
        assertThat(testBook.getAuthors()).isNotNull().isNotEmpty().hasSize(2)
                .extracting("firstName", "lastName")
                .contains(Tuple.tuple(author1.getFirstName(), author1.getLastName()),
                        Tuple.tuple(author2.getFirstName(), author2.getLastName()));
    }
}