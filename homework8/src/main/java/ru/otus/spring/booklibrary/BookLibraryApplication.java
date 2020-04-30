package ru.otus.spring.booklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class BookLibraryApplication {

	public static void main(String[] args) throws SQLException {
		ConfigurableApplicationContext context = SpringApplication.run(BookLibraryApplication.class, args);
		context.close();
	}

}
