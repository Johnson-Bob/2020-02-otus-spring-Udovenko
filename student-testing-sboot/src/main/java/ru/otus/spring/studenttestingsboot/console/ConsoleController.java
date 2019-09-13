package ru.otus.spring.studenttestingsboot.console;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.studenttestingsboot.dto.AnswerDto;
import ru.otus.spring.studenttestingsboot.dto.ResultDto;
import ru.otus.spring.studenttestingsboot.entity.Answer;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.entity.User;
import ru.otus.spring.studenttestingsboot.service.InternationalizationService;
import ru.otus.spring.studenttestingsboot.service.TestingService;
import ru.otus.spring.studenttestingsboot.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class ConsoleController implements AutoCloseable {

    private final UserService userService;
    private final TestingService testingService;
    private final InternationalizationService i18nService;
    private User user;
    private Scanner scanner = new Scanner(System.in);

    public User createUser() {
        user = new User();
        user.setFirstName(printAndWait(i18nService.getMessage("enter.first.name"), Pattern.compile("^\\D+\\b")));
        user.setLastName(printAndWait(i18nService.getMessage("enter.last.name"), Pattern.compile("^\\D+\\b")));
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

            char userAnswer = printAndWait(i18nService.getMessage("choose.letter"), Pattern.compile("\\b[a-dA-Dа-гА-Г]")).charAt(0);
            AnswerDto answerDto = new AnswerDto(user.getId(), question.getId(), userAnswer);
            answerDto = testingService.saveAnswer(answerDto);

            if (answerDto.isUserAnswerCorrect()) {
                System.out.println(i18nService.getMessage("answer.congratulations"));
            } else {
                System.out.println(i18nService.getMessage("answer.unfortunately"));
                System.out.println(i18nService.getMessage("answer.correct"));
                System.out.println(answerDto.getCorrectAnswer());
            }
        }

        return testingService.getTestResult(user.getId());
    }

    public void printResult(ResultDto result) {
        System.out.println(i18nService.getMessage("test.result", result.getUser().toString(), result.getResult()));
        System.out.println(result.isTestPass() ? i18nService.getMessage("test.passed") : i18nService.getMessage("test.failed"));
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
