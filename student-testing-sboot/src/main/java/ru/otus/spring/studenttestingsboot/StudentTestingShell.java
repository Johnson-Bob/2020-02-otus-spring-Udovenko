package ru.otus.spring.studenttestingsboot;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.studenttestingsboot.cache.UpdateCacheService;
import ru.otus.spring.studenttestingsboot.console.ConsoleController;
import ru.otus.spring.studenttestingsboot.service.InternationalizationService;

@ShellComponent
@RequiredArgsConstructor
public class StudentTestingShell {
    private final ConsoleController consoleController;
    private final UpdateCacheService updateCacheService;
    private final InternationalizationService i18nService;
    private boolean isLogin;
    private boolean isAnswered;

    @ShellMethod(value = "Login user", key = {"login", "ln"})
    public void login() {
        consoleController.createUser();
        isLogin = true;
    }

    @ShellMethod(value = "Testing", key = {"start-test", "st"})
    @ShellMethodAvailability("checkLogin")
    public void startTest() {
        consoleController.askQuestions();
        isAnswered = true;
    }

    @ShellMethod(value = "Print result", key = {"result", "rt"})
    @ShellMethodAvailability("checkAnswered")
    public void getResult() {
        consoleController.printResult();
        isLogin = false;
        isAnswered = false;
    }

    @ShellMethod(value = "Rereading questions from file", key = {"read-from-file", "update", "u"})
    public String rereadQuestionsFromFile() {
        updateCacheService.updateCache();

        return "Questions has been read successfully";
    }

    public Availability checkLogin() {
        return isLogin ? Availability.available() : Availability.unavailable("you must be logged");
    }

    public Availability checkAnswered() {
        return isAnswered ? Availability.available() : Availability.unavailable("you must answered to all question before");
    }
}
