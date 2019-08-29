package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.dto.ResultDto;
import ru.otus.spring.presentation.console.ConsoleController;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/source.xml");

        try (ConsoleController controller = context.getBean(ConsoleController.class)) {
            controller.createUser();
            ResultDto result = controller.askQuestions();

            System.out.println(String.format("%s, your result is: %s", result.getUser(), result.getResult()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
