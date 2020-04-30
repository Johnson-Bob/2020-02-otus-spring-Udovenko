package ru.otus.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with book")
@ExtendWith(SpringExtension.class)
@DataMongoTest
class BookDaoJpaTest {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private MongoTemplate mt;

    @Test
    @DisplayName("should return correct book by book's title")
    void shouldGetBookByType() {
        Book newBook = new Book(null, "Test title", "Fantasy", null);
        mt.insert(newBook);

        List<Book> booksFromDatabase = bookDao.findByTitle(newBook.getTitle());

        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(1);
        Book bookFromDb = booksFromDatabase.get(0);
        assertThat(bookFromDb.getId()).isEqualTo(newBook.getId());
        assertThat(bookFromDb).isEqualToComparingOnlyGivenFields(newBook, "title", "genre");
    }

    @Test
    @DisplayName("should return empty list when don't find book by name")
    void shouldReturnEmptyList() {
        String bookName = "abracatabra";

        List<Book> foundBooks = bookDao.findByTitle(bookName);

        assertThat(foundBooks).isNotNull().isEmpty();
    }
}