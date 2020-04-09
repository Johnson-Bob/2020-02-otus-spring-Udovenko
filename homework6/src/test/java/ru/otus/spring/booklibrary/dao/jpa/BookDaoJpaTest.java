package ru.otus.spring.booklibrary.dao.jpa;

import org.assertj.core.groups.Tuple;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with book")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {
    @Autowired
    private BookDaoJpa bookDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("should return all books")
    void shouldGetAll() {
        List<Book> booksFromDatabase = bookDao.getAll();

        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(2)
                .allMatch(b -> StringUtils.isNotBlank(b.getName()))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> !b.getAuthors().isEmpty());
    }

    @Test
    @DisplayName("should return correct book by id")
    void shouldGetCorrectBookById() {
        Book bookFromDatabase = bookDao.getById(1L);
        Book existingBook = em.find(Book.class, 1L);

        assertTwoBooks(bookFromDatabase, existingBook);
    }

    @Test
    @DisplayName("should return correct book by book's name")
    void shouldGetBookByName() {
        Book existingBook = em.find(Book.class, 1L);

        List<Book> booksFromDatabase = bookDao.getByName(existingBook.getName());

        assertThat(booksFromDatabase).isNotNull().isNotEmpty().hasSize(1);
        Book bookFromDb = booksFromDatabase.get(0);
        assertThat(bookFromDb.getId()).isEqualTo(existingBook.getId());
        assertTwoBooks(bookFromDb, existingBook);
    }

    @Test
    @DisplayName("should return empty list when don't find book by name")
    void shouldReturnEmptyList() {
        String bookName = "abracatabra";

        List<Book> foundBooks = bookDao.getByName(bookName);

        assertThat(foundBooks).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("should save book in database and create relation with authors")
    void shouldSaveBookInDatabase() {
        Book newBook = getNewBook();

        Book savedBook = bookDao.save(newBook);
        Book bookFromDb = em.find(Book.class, savedBook.getId());

        assertThat(savedBook.getId()).isNotNull();
        assertTwoBooks(bookFromDb, newBook);
    }

    @Test
    @DisplayName("should delete book from database and delete relations with authors")
    void shouldDeleteBookFromDatabase() {
        Book existingBook =em.find(Book.class, 2L);
        bookDao.deleteById(2L);

        Session session = em.getEntityManager().unwrap(Session.class);
        List<?> remainingRelations = session.createNativeQuery("SELECT * FROM book_author WHERE book_id = 2").list();

        Book afterDeleted = em.find(Book.class, existingBook.getId());
        assertThat(afterDeleted).isNull();
        assertThat(remainingRelations).isEmpty();
    }

    private Book getNewBook() {
        Genre genre = em.find(Genre.class, 1L);
        Author author1 = em.find(Author.class, 1L);
        Author author2 = em.find(Author.class, 3L);
        return new Book(null, "My new book", genre, Set.of(author1, author2));
    }

    private void assertTwoBooks(Book testBook, Book exampleBook) {
        Iterator<Author> iterator= exampleBook.getAuthors().iterator();
        Author author1 = iterator.next();
        Author author2 = iterator.next();

        assertThat(testBook).isNotNull().isEqualToComparingOnlyGivenFields(exampleBook, "name");
        assertThat(testBook.getGenre()).isNotNull().isEqualToComparingFieldByField(exampleBook.getGenre());
        assertThat(testBook.getAuthors()).isNotNull().isNotEmpty().hasSize(2)
                .extracting("firstName", "lastName")
                .contains(Tuple.tuple(author1.getFirstName(), author1.getLastName()),
                        Tuple.tuple(author2.getFirstName(), author2.getLastName()));
    }
}