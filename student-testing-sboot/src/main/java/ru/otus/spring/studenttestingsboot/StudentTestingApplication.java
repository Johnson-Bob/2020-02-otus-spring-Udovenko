package ru.otus.spring.studenttestingsboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.studenttestingsboot.console.ConsoleController;
import ru.otus.spring.studenttestingsboot.dto.ResultDto;

@SpringBootApplication
public class StudentTestingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StudentTestingApplication.class, args);

		try (ConsoleController controller = context.getBean(ConsoleController.class)) {
			controller.createUser();
			ResultDto result = controller.askQuestions();
			controller.printResult(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

}
