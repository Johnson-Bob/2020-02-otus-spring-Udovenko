package ru.otus.spring.booklibrary.dao.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM genre;", BeanPropertyRowMapper.newInstance(Genre.class));
    }
}
