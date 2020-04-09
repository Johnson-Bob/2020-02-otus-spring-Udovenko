package ru.otus.spring.booklibrary.dao.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.CommentDao;
import ru.otus.spring.booklibrary.model.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao {
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBook_Id(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c INNER JOIN c.book b WHERE b.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        Comment comment = em.getReference(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
            em.flush();
        }
    }
}
