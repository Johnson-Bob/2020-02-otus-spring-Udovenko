package ru.otus.spring.booklibrary.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDaoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> getAllAuthors() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findByFirstAndLastNames(String firstName, String lastName) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName", Author.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return getSingleResult(query);
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }

        return author;
    }

    private Optional<Author> getSingleResult(TypedQuery<Author> query) {
        Author author = null;
        try {
            author = query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.debug("Author with parameters <{}> not found", query.getParameters());
        }

        return Optional.ofNullable(author);
    }
}
