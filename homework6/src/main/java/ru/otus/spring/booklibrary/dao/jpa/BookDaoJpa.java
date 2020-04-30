package ru.otus.spring.booklibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.model.entity.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("bookGenre");
        TypedQuery<Book> allBook = em.createQuery("SELECT b FROM Book b", Book.class);
        allBook.setHint("javax.persistence.fetchgraph", entityGraph);
        return allBook.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(Long id) {
       return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getByName(String name) {
        EntityGraph<?> entityGraph = em.getEntityGraph("bookGenre");
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.name = :name", Book.class);
        query.setParameter("name", name);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public void deleteById(Long id) {
        Book book = em.getReference(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
