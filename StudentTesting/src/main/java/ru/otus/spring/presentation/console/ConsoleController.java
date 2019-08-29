package ru.otus.spring.presentation.console;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dto.AnswerDto;
import ru.otus.spring.dto.ResultDto;
import ru.otus.spring.entity.Answer;
import ru.otus.spring.entity.Question;
import ru.otus.spring.entity.User;
import ru.otus.spring.service.TestingService;
import ru.otus.spring.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ConsoleController implements AutoCloseable {

    private final UserService userService;
    private final TestingService testingService;
    private User user;
    private Scanner scanner = new Scanner(System.in);

    public User createUser() {
        user = new User();
        user.setFirstName(printAndWait("Please, enter your first name:", Pattern.compile("^\\D+\\b")));
        user.setLastName(printAndWait("Please, enter your last name:", Pattern.compile("^\\D+\\b")));
        user = userService.createUser(user);

        return user;

    }

    public ResultDto askQuestions() {
        List<Question> questions = testingService.getAllQuestion();
        for (Question question : questions) {
            System.out.println(question);
            for (Answer answer : question.getAnswers()) {
                System.out.println(answer);
            }

            char userAnswer = printAndWait("Please, choose letter that related your answer", Pattern.compile("\\b[a-dA-D]")).charAt(0);
            AnswerDto answerDto = new AnswerDto(user.getId(), question.getId(), userAnswer);
            answerDto = testingService.saveAnswer(answerDto);

            if (answerDto.isUserAnswerCorrect()) {
                System.out.println("Congratulate, your answer is correct");
            } else {
                System.out.println("Unfortunately your answer is incorrect");
                System.out.println("Correct answer is:");
                System.out.println(answerDto.getCorrectAnswer());
            }
        }

        return testingService.getTestResult(user.getId());
    }

    private String printAndWait(String invite, Pattern pattern) {
        String result;
        do {
            System.out.println(invite);
            String line = scanner.next();
            result = pattern.matcher(line).find() ? line : null;
        } while (result == null);

        return result;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
