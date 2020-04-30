package ru.otus.spring.booklibrary.dao.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with genre")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Test
    @DisplayName("Should return list of all genres")
    void shouldReturnListOfAuthors() {
        List<Genre> genres = genreDaoJpa.getAllGenres();

        assertThat(genres).isNotNull().isNotEmpty().hasSize(4)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getGenreName()));
    }

}