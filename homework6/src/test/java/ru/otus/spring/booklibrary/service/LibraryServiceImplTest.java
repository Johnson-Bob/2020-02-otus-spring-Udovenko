package ru.otus.spring.booklibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.spring.booklibrary.TestUtils;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LibraryServiceImpl work with library")
@SpringBootTest
class LibraryServiceImplTest {
    @Autowired private LibraryServiceImpl libraryService;
    @MockBean private BookDao bookDao;
    @MockBean private AuthorDao authorDao;
    @MockBean private GenreDao genreDao;

    @BeforeEach
    void setUp() {
        Mockito.when(bookDao.create(ArgumentMatchers.any(Book.class))).thenReturn(Long.valueOf(3));
    }

    @Test
    @DisplayName("should create new Book and save it in database")
    void shouldSaveBookInDatabase() {
//        Arrange
        Book testBook = TestUtils.createNewTestBook();
        BookDto bookDto = TestUtils.createTestBookDto();

//        Act
        Book bookAfterSave = libraryService.addBookToLibrary(bookDto);

//        Assert
        assertThat(bookAfterSave.getId()).isEqualTo(3);
        TestUtils.assertTwoBooks(bookAfterSave, testBook);
    }

    @SpringBootConfiguration
    static class LibraryServiceImplTestConfiguration {
        @Bean
        @Autowired
        LibraryService libraryService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
            return new LibraryServiceImpl(bookDao, authorDao, genreDao);
        }
    }
}