package ru.otus.spring.booklibrary.dao.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with genre")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("Should return list of all genres")
    void shouldReturnListOfAuthors() {
//        Act
        List<Genre> genres = genreDao.getAllGenres();

//        Assert
        assertThat(genres).isNotNull().isNotEmpty().hasSize(4)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getGenreName()));
    }
}