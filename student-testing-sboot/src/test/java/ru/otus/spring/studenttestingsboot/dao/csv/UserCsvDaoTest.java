package ru.otus.spring.studenttestingsboot.dao.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.studenttestingsboot.TestUtil;
import ru.otus.spring.studenttestingsboot.entity.User;
import ru.otus.spring.studenttestingsboot.exception.DaoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserCsvDaoTest {
    private final static String FIRST_NAME = "TestFirstName";
    private final static String LAST_NAME = "TestLastName";

    UserCsvDao userCsvDao;
    User testUser1;

    @BeforeEach
    void setUp() {
        userCsvDao = new UserCsvDao();
        testUser1 = TestUtil.createUser(FIRST_NAME, LAST_NAME);
    }

    @Test
    public void getUserWithIdWhenSaveNewUser() {

//        Act
        User afterSaveUser = userCsvDao.save(testUser1);

//        Assert
        assertThat(afterSaveUser)
                .isNotNull()
                .hasFieldOrPropertyWithValue("firstName", FIRST_NAME)
                .hasFieldOrPropertyWithValue("lastName", LAST_NAME)
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    public void getAllUserFromRepositoryAfterSaveTheir() {
//        Arrange
        String firstName2 = "TestFirstName2";
        String secondName2 = "TestSecondName2";
        User testUser2 = TestUtil.createUser(firstName2, secondName2);

//        Act
        userCsvDao.save(testUser1);
        userCsvDao.save(testUser2);
        List<User> userListFromRepository = userCsvDao.getAll();

//        Assert
        User userFromRep1 = userListFromRepository.stream().filter(u -> u.getId() == 1).findAny().orElseThrow(() -> new RuntimeException());
        User userFromRep2 = userListFromRepository.stream().filter(u -> u.getId() == 2).findAny().orElseThrow(() -> new RuntimeException());
        assertThat(userListFromRepository.size()).isEqualTo(2);
        assertThat(userFromRep1).usingRecursiveComparison().ignoringFields("id").isEqualTo(testUser1);
        assertThat(userFromRep2).usingRecursiveComparison().ignoringFields("id").isEqualTo(testUser2);
    }

    @Test
    public void getUserByIdFromRepositoryAfterSave() {
//        Act
        userCsvDao.save(testUser1);
        User userFromRep = userCsvDao.getById(1);

//        Assert
        assertThat(userFromRep)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(testUser1);
    }

    @Test
    public void throwDaoExceptionWhenGetUserByIncorrectIdAfterSave() {
//        Act
        userCsvDao.save(testUser1);

//        Assert
        long id = 3;
        assertThatExceptionOfType(DaoException.class)
                .isThrownBy(() -> userCsvDao.getById(id))
                .withMessage(String.format("User with ID=%d not found", id));

    }
}