package ru.otus.spring.studenttestingsboot.dto;

import lombok.Builder;
import lombok.Getter;
import ru.otus.spring.studenttestingsboot.entity.User;

@Getter
@Builder
public class ResultDto {
    private User user;
    private String result;
    private boolean isTestPass;
}
