package ru.otus.spring.studenttestingsboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.studenttestingsboot.annotation.UpdateCache;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.dto.AnswerDto;
import ru.otus.spring.studenttestingsboot.dto.ResultDto;
import ru.otus.spring.studenttestingsboot.entity.Answer;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.entity.User;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestingServiceImpl implements TestingService {
    private final Dao<Question> questionDao;
    private final UserService userService;

    private Map<Long, Answer> correctAnswerCache;
    private int sumOfCorrectAnswers; //was too lazy to create a normal repository for answers. Sorry
    @Value("${pass.test}")
    private float testPass;

    @PostConstruct
    public void init() {
        fillCorrectAnswerCache(questionDao.getAll());
    }

    @Override
    public List<Question> getAllQuestion() {
        List<Question> questions = questionDao.getAll();
        return questions;
    }

    @Override
    public AnswerDto saveAnswer(AnswerDto answerDto) {
        checkAnswer(answerDto);
        if (answerDto.isUserAnswerCorrect()) {
            sumOfCorrectAnswers++;
        }
        return answerDto;
    }

    @Override
    public ResultDto getTestResult(long userId) {
        User user = userService.getUserById(userId);
        float percentOfCorrectAnswers = 100f * ((float) sumOfCorrectAnswers / getAllQuestion().size());

        return ResultDto.builder()
                .user(user)
                .result(String.format("%.1f%%", percentOfCorrectAnswers))
                .isTestPass(percentOfCorrectAnswers >= testPass).build();
    }

    private void fillCorrectAnswerCache(List<Question> questions) {
        correctAnswerCache = new HashMap<>();
        for (Question question : questions) {
            List<Answer> correctAnswers = question.getAnswers().stream()
                    .filter(Answer::isCorrect)
                    .collect(Collectors.toList());

            if (correctAnswers.isEmpty() || correctAnswers.size() > 1) {
                throw new IllegalArgumentException(String.format("The question with ID=%d don't have or have more than one correct answer", question.getId()));
            }

            correctAnswerCache.put(question.getId(), correctAnswers.get(0));
        }
    }

    @UpdateCache
    private void updateCache() {
        fillCorrectAnswerCache(questionDao.updateCache());
    }

    private void checkAnswer(AnswerDto answerDto) {
        Answer correctAnswer = correctAnswerCache.get(answerDto.getQuestionId());
        if (correctAnswer != null) {
            answerDto.setUserAnswerCorrect(Character.toUpperCase(answerDto.getUserAnswer()) == correctAnswer.getLetter());
            answerDto.setCorrectAnswer(correctAnswer);
        }
    }
}
