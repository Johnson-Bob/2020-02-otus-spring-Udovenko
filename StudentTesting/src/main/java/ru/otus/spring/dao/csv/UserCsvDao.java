package ru.otus.spring.dao.csv;

import ru.otus.spring.dao.Dao;
import ru.otus.spring.entity.User;
import ru.otus.spring.exception.DaoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return userRepository.get(id);
    }
}
