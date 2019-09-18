package ru.otus.spring.studenttestingsboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.studenttestingsboot.TestUtil;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.dto.AnswerDto;
import ru.otus.spring.studenttestingsboot.dto.ResultDto;
import ru.otus.spring.studenttestingsboot.entity.Answer;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Class TestingServiceImpl")
class TestingServiceImplTest {
    @MockBean
    private Dao<Question> questionDao;
    @MockBean
    private UserService userService;
    @Autowired
    private TestingServiceImpl testingService;

    private User user = TestUtil.createUser("TestFirstName", "TestLastName");

    @BeforeEach
    void setUp() {
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(questionDao.getAll()).thenReturn(TestUtil.createQuestionList());
    }

    @Test
    @DisplayName("return List<Questions> when called method getAllQuestion()")
    void returnListOfQuestions() {
//        Act
        List<Question> returnedList = testingService.getAllQuestion();

//        Assert
        assertThat(returnedList).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("return isCorrectAnswer = true if answer correct")
    void returnCorrectAnswer() {
//        Arrange
        testingService.init();
        Answer correctAnswer = TestUtil.createQuestionList().stream()
                .filter(q -> q.getId() == 1)
                .flatMap(q -> q.getAnswers().stream())
                .filter(Answer::isCorrect).findAny().orElseThrow(IllegalAccessError::new);

//        Act
        AnswerDto returnedDto = testingService.saveAnswer(new AnswerDto(1, 1, 'c'));

//        Assert
        assertThat(returnedDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("isUserAnswerCorrect", true)
                .hasFieldOrPropertyWithValue("correctAnswer", correctAnswer);
    }

    @Test
    @DisplayName("return isCorrectAnswer = false if answer incorrect")
    void returnIncorrectAnswer() {
//        Arrange
        testingService.init();
        Answer correctAnswer = TestUtil.createQuestionList().stream()
                .filter(q -> q.getId() == 1)
                .flatMap(q -> q.getAnswers().stream())
                .filter(Answer::isCorrect).findAny().orElseThrow(IllegalAccessError::new);

//        Act
        AnswerDto returnedDto = testingService.saveAnswer(new AnswerDto(1, 1, 'a'));

//        Assert
        assertThat(returnedDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("isUserAnswerCorrect", false)
                .hasFieldOrPropertyWithValue("correctAnswer", correctAnswer);
    }

    @Test
    @DisplayName("return result of test")
    void resultOfTest() {
//        Act
        ResultDto resultOfTest = testingService.getTestResult(1);

//        Assert
        assertThat(resultOfTest).isNotNull();
    }

    @SpringBootConfiguration
    public static class TestingServiceImplTestConfiguration {
        @Bean
        public TestingServiceImpl testingService(Dao<Question> questionDao, UserService userService) {
            return new TestingServiceImpl(questionDao, userService);
        }

    }

}