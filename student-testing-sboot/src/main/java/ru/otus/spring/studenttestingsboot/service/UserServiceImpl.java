package ru.otus.spring.studenttestingsboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.entity.User;

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
