package ru.otus.spring.studenttestingsboot.dao.csv;

import org.springframework.stereotype.Repository;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.entity.User;
import ru.otus.spring.studenttestingsboot.exception.DaoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserCsvDao<T> implements Dao<User> {
    private Map<Long, User> userRepository = new HashMap<>();

    @Override
    public List<User> getAll() throws DaoException {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public User save(User user) throws DaoException {
        long maxId = userRepository.keySet().stream().mapToLong(Long::longValue).max().orElse(0);
        user.setId(++maxId);
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(long id) throws DaoException {
        User result = userRepository.get(id);

        if (result == null) {
            throw new DaoException(String.format("User with ID=%d not found", id));
        }

        return result;
    }
}
