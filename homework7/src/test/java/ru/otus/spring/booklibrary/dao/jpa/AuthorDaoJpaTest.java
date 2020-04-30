package ru.otus.spring.booklibrary.dao.jpa;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with author")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    @Autowired
    private AuthorDaoJpa authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Should return list of all authors")
    void shouldReturnListOfAuthors() {
        List<Author> authors = authorDao.getAllAuthors();

        assertThat(authors).isNotNull().isNotEmpty().hasSize(4)
                .allMatch(a -> a.getId() != 0)
                .allMatch(a -> StringUtils.isNotBlank(a.getFirstName()))
                .allMatch(a -> StringUtils.isNotBlank(a.getLastName()));
    }

    @Test
    @DisplayName("should return correct author by author's fist and second name")
    void shouldGetAuthorByFirstAndLastNames() {
        Author existingAuthor = em.find(Author.class, 1L);

        Author authFromDb = authorDao.findByFirstAndLastNames(existingAuthor.getFirstName(), existingAuthor.getLastName()).get();

        assertThat(authFromDb).isNotNull();
        assertThat(authFromDb).isEqualTo(existingAuthor);
    }

    @Test
    @DisplayName("should save author in database")
    void shouldSaveAuthorInDatabase() {
        Author newAuthor = new Author(null, "First name", "Last name");

        Author savedAuthor = authorDao.save(newAuthor);
        Author authFromDb = em.find(Author.class, savedAuthor.getId());

        assertThat(savedAuthor.getId()).isNotNull();
        assertThat(authFromDb).isEqualToIgnoringGivenFields(newAuthor, "id");
    }
}