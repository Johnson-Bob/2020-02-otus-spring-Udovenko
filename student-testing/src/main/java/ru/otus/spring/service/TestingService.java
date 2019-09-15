package ru.otus.spring.service;

import ru.otus.spring.dto.AnswerDto;
import ru.otus.spring.dto.ResultDto;
import ru.otus.spring.entity.Question;

import java.util.List;

public interface TestingService {
    List<Question> getAllQuestion();
    AnswerDto saveAnswer(AnswerDto answerDto);
    ResultDto getTestResult(long userId);
}
