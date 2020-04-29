package ru.otus.spring.booklibrary.dao.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.spring.booklibrary.dao.CommentDao;
import ru.otus.spring.booklibrary.model.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c INNER JOIN c.book b WHERE b.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Comment createComment(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public void deleteCommentById(Long id) {
        Comment comment = em.getReference(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
    }
}
