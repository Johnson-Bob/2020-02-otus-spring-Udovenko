package ru.otus.spring.studenttestingsboot.cache;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.studenttestingsboot.TestUtil;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.dto.AnswerDto;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.service.TestingService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Class UpdateCacheService")
class UpdateCacheServiceTest {
    @Autowired private TestingService testingService;
    @Autowired private UpdateCacheService updateCache;
    @MockBean private Dao<Question> questionDao;

    @BeforeEach
    void beforeClass() {

    }

    @Test
    @DisplayName("update Correct Answer Cache")
    public void updateCacheTest() {
        Assertions.assertThat(testingService.saveAnswer(new AnswerDto(1, 1, 'c')))
                .isNotNull()
                .hasFieldOrPropertyWithValue("isUserAnswerCorrect", false);

        Mockito.when(questionDao.updateCache()).thenReturn(TestUtil.createQuestionList());
        updateCache.updateCache();

        Assertions.assertThat(testingService.saveAnswer(new AnswerDto(1, 1, 'c')))
                .isNotNull()
                .hasFieldOrPropertyWithValue("isUserAnswerCorrect", true);
    }

}