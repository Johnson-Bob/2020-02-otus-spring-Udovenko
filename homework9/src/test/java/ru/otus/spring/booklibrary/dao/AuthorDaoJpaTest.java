package ru.otus.spring.booklibrary.dao;

import com.github.mongobee.Mongobee;
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
import ru.otus.spring.booklibrary.model.entity.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with author")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoBeeConfig.class)
class AuthorDaoJpaTest {
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private MongoTemplate mt;

    @Test
    @DisplayName("should return correct author by author's fist and second name")
    void shouldGetAuthorByFirstAndLastNames() {
        Author existAuth = mt.findAll(Author.class).get(0);

        Author authFromDb = authorDao
                .findByFirstNameAndLastName(existAuth.getFirstName(), existAuth.getLastName())
                .orElseThrow();

        assertThat(authFromDb).isNotNull();
        assertThat(authFromDb).isEqualTo(existAuth);
    }
}