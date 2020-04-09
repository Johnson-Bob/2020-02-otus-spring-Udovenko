package ru.otus.spring.booklibrary.dao.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with comment")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("should return correct book comment list")
    void shouldReturnCommentList() {
        Book bookFromDb = em.find(Book.class, 1L);
        List<Comment> comments = dao.findByBook_Id(bookFromDb.getId());

        assertThat(comments).isNotNull().hasSize(2);
        assertThat(comments).extracting(Comment::getBook)
                .allMatch(b -> b.getId() == bookFromDb.getId())
                .allMatch(b -> b.getName().equals(bookFromDb.getName()));
    }

    @Test
    @DisplayName("should save new comment")
    void shouldSaveComment() {
        String testComment = "Test comment";
        Comment comment = new Comment(null, new Book(2l, null, null, null), testComment);

        Comment afterSave = dao.createComment(comment);

        assertThat(afterSave)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(comment, "id");
    }

    @Test
    @DisplayName("should delete a comment and don't delete a book")
    void shouldDeleteComment() {
        Comment existingComment = em.find(Comment.class, 1L);

        dao.deleteCommentById(existingComment.getId());

        Comment deletedComment = em.find(Comment.class, existingComment.getId());
        assertThat(deletedComment).isNull();

        Book commentedBook = em.find(Book.class, existingComment.getBook().getId());
        assertThat(commentedBook).isNotNull();
    }
}