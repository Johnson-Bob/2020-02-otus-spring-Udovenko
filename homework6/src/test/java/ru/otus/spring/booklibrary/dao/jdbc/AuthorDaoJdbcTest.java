package ru.otus.spring.booklibrary.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with author")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    @DisplayName("Should return list of used authors")
    void shouldReturnListUsedAuthors() {
//        Act
        List<Author> usedAuthors = authorDao.getAllUsed();

//        Assert
        assertThat(usedAuthors).isNotNull().isNotEmpty().hasSize(3)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getFirstName()))
                .allMatch(a -> StringUtils.isNotBlank(a.getLastName()));
    }

    @Test
    @DisplayName("Should return correct list of authors by book's ID")
    void shouldReturnAuthorsByBookId() {
//        Act
        List<Author> authorsByBookId = authorDao.getByBookId(1);

//        Assert
        assertThat(authorsByBookId).isNotNull().isNotEmpty().hasSize(2)
                .allMatch(a -> a.getId() > 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getFirstName()))
                .allMatch(a -> StringUtils.isNotBlank(a.getLastName()));
    }

    @Test
    @DisplayName("Should return list of all authors")
    void shouldReturnListOfAuthors() {
//        Act
        List<Author> authors = authorDao.getAllAuthors();

//        Assert
        assertThat(authors).isNotNull().isNotEmpty().hasSize(4)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getFirstName()))
                .allMatch(a -> StringUtils.isNotBlank(a.getLastName()));
    }
}