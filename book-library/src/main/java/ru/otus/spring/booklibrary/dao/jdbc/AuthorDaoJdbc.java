package ru.otus.spring.booklibrary.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllUsed() {
        return jdbcTemplate.query("SELECT a.id, a.first_name, a.last_name " +
                "FROM author a INNER JOIN book_author ba ON a.id = ba.author_id", BeanPropertyRowMapper.newInstance(Author.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getByBookId(long id) {
        return jdbcTemplate.query("SELECT a.id, a.first_name, a.last_name " +
                "FROM author a INNER JOIN book_author ba ON a.id = ba.author_id " +
                "WHERE ba.book_id = ?", new Long[]{id}, BeanPropertyRowMapper.newInstance(Author.class));
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM author;", BeanPropertyRowMapper.newInstance(Author.class));
    }
}
