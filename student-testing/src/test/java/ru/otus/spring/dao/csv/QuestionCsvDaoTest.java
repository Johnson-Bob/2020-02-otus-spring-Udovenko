package ru.otus.spring.dao.csv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.TestUtil;
import ru.otus.spring.entity.Question;
import ru.otus.spring.exception.DaoException;
import ru.otus.spring.service.InternationalizationService;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = QuestionCsvDaoTest.QuestionCsvDaoTestConfig.class)
class QuestionCsvDaoTest {

    @Autowired
    private QuestionCsvDao questionCsvDao;

    @Autowired
    private InternationalizationService i18nService;

    @Autowired
    private ApplicationContext context;

    @Test
    public void readFromFileQuestionsThenReturnListQuestion() {
//        Arrange
        List<Question> expectedList = TestUtil.createQuestionList();

//        Act
        List<Question> resultList = questionCsvDao.getAll();

//        Assert
        assertThat(expectedList).containsAll(resultList);
        assertThat(expectedList.get(0).getAnswers()).containsAll(resultList.get(0).getAnswers());
        assertThat(expectedList.get(1).getAnswers()).containsAll(resultList.get(1).getAnswers());
    }

    @Test
    public void readFromFileQuestionsThenReturnQuestionByCorrectId() {
//        Arrange
        Question expectedQuestion = TestUtil.createQuestionList().stream()
                .filter(q -> q.getId() == 2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

//        Act
        Question resultQuestion = questionCsvDao.getById(2);

//        Assert
        assertThat(expectedQuestion).isEqualTo(resultQuestion);
        assertThat(expectedQuestion.getAnswers()).containsAll(resultQuestion.getAnswers());
    }

    @Test
    public void readFromFileThenThrowDaoExceptionWhenGetByIncorrectId() {
//        Assert
        long id = 3;
        assertThatExceptionOfType(DaoException.class)
                .isThrownBy(() -> questionCsvDao.getById(id))
                .withMessage(String.format("Question with ID=%d not found", id));
    }

    @Test
    public void readFromFileWithRuQuestionsWhenSetRuLocale() {
//        Arrange
        i18nService.setCurrentLocale(new Locale("ru"));
        QuestionCsvDao questionCsvDao = context.getBean(QuestionCsvDao.class);
        questionCsvDao.readFromFile();

//        Act
        Question question = questionCsvDao.getById(1);

//        Assert
        assertThat(question.getText()).isEqualTo("Тестовый вопрос 1?");
    }



    @Configuration
    @PropertySource("classpath:application.properties")
    public static class QuestionCsvDaoTestConfig {

        @Bean
        @Scope("prototype")
        public QuestionCsvDao questionCsvDao() {
            return new QuestionCsvDao();
        }

        @Bean
        public InternationalizationService i18nService() {
            return new InternationalizationService();
        }


        @Bean
        public PropertySourcesPlaceholderConfigurer propertyConfigure() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}