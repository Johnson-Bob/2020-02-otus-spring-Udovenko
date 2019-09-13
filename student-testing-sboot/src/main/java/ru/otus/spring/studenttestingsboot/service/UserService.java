package ru.otus.spring.studenttestingsboot.service;

import ru.otus.spring.studenttestingsboot.entity.User;

public interface UserService {
    User createUser(User user);
    User getUserById(long id);
}
