package ru.otus.spring.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.otus.spring.booklibrary.dao")
public class BookLibraryApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BookLibraryApplication.class, args);
		context.close();
	}
}
