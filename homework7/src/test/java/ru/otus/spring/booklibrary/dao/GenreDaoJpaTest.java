package ru.otus.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with genre")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDaoJpa;

    @Test
    @DisplayName("Should return list of all genres")
    void shouldReturnListOfAuthors() {
        Iterable<Genre> genres = genreDaoJpa.findAll();

        assertThat(genres).isNotNull().isNotEmpty().hasSize(4)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getGenreName()));
    }

}