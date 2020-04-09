package ru.otus.spring.booklibrary;

import org.assertj.core.groups.Tuple;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
    public static Book createNewTestBook() {
        Genre genre = new Genre(4, "Adventure");
        List authors = List.of(new Author(1, "Ivan", "Ivanov"),
                new Author(3, "Sidor", "Sidorov"));

        return new Book(0, "My new book", genre, authors);
    }

    public static BookDto createTestBookDto() {
        Book book = createNewTestBook();
        return BookDto.builder()
                .bookName(book.getName())
                .genre(book.getGenre())
                .authors(book.getAuthors())
                .build();
    }

    public static Book createExistingTestBook() {
        Genre genre = new Genre(1, "Science fiction");
        List authors = List.of(new Author(1, "Ivan", "Ivanov"),
                new Author(2, "Petr", "Petrov"));

        return new Book(1, "My first book", genre, authors);
    }

    public static void assertTwoBooks(Book bookFromDatabase, Book testBook) {
        assertThat(bookFromDatabase).isNotNull().isEqualToComparingOnlyGivenFields(testBook, "name");
        assertThat(bookFromDatabase.getGenre()).isNotNull().isEqualToComparingFieldByField(testBook.getGenre());
        assertThat(bookFromDatabase.getAuthors()).isNotNull().isNotEmpty().hasSize(2)
                .extracting("firstName", "lastName")
                .contains(Tuple.tuple(testBook.getAuthors().get(0).getFirstName(), testBook.getAuthors().get(0).getLastName()),
                        Tuple.tuple(testBook.getAuthors().get(1).getFirstName(), testBook.getAuthors().get(1).getLastName()));
    }
}
