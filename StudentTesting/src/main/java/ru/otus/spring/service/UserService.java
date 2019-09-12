package ru.otus.spring.service;

import ru.otus.spring.entity.User;

public interface UserService {
    User createUser (User user);
    User getUserById(long id);
}
