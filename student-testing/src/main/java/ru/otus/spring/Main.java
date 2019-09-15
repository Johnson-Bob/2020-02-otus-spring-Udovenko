package ru.otus.spring;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.dto.ResultDto;
import ru.otus.spring.presentation.console.ConsoleController;

import java.util.Properties;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        try (ConsoleController controller = context.getBean(ConsoleController.class)) {
            controller.createUser();
            ResultDto result = controller.askQuestions();
            controller.printResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
