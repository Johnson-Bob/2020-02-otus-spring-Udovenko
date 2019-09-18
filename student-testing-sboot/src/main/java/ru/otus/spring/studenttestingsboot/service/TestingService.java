package ru.otus.spring.studenttestingsboot.service;

import ru.otus.spring.studenttestingsboot.dto.AnswerDto;
import ru.otus.spring.studenttestingsboot.dto.ResultDto;
import ru.otus.spring.studenttestingsboot.entity.Question;

import java.util.List;

public interface TestingService {
    List<Question> getAllQuestion();
    AnswerDto saveAnswer(AnswerDto answerDto);
    ResultDto getTestResult(long userId);
}
