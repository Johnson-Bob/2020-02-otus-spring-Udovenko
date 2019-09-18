package ru.otus.spring.studenttestingsboot.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.spring.studenttestingsboot.entity.Answer;

@Getter
@Setter
@RequiredArgsConstructor
public class AnswerDto {
    private final long userId;
    private final long questionId;
    private final char userAnswer;
    private Answer correctAnswer;
    private boolean isUserAnswerCorrect;
}
