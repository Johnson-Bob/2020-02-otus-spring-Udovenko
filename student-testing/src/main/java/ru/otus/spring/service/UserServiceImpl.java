package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.Dao;
import ru.otus.spring.entity.User;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final Dao<User> userDao;
    @Override
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getById(id);
    }
}
