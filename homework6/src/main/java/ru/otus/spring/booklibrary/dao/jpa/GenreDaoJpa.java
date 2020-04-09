package ru.otus.spring.booklibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.entity.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Genre> getAllGenres() {
        return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
    }
}
