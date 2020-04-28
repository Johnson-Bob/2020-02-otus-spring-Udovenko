package ru.otus.spring.booklibrary.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO for work with comment")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CommentDaoJpaTest {

    @Autowired
    private CommentDao dao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("should save new comment")
    void shouldSaveComment() {
        String testComment = "Test comment";
        Book book = new Book();
        book.setId(2L);
        Comment comment = new Comment(null, book, testComment);

        Comment afterSave = dao.save(comment);

        assertThat(afterSave)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualToIgnoringGivenFields(comment, "id");
    }

    @Test
    @DisplayName("should delete a comment and don't delete a book")
    void shouldDeleteComment() {
        Comment existingComment = em.find(Comment.class, 1L);
        /*Book commentedBook = em.find(Book.class, existingComment.getBook().getId());
        int commentsCount = commentedBook.getComments().size();*/

        dao.deleteById(existingComment.getId());

        Comment deletedComment = em.find(Comment.class, existingComment.getId());
        assertThat(deletedComment).isNull();

        Book commentedBook = em.find(Book.class, existingComment.getBook().getId());
        assertThat(commentedBook).isNotNull();
    }
}