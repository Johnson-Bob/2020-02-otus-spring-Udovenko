package ru.otus.spring.booklibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
public class BookLibraryApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Configuration
	@TestPropertySource(locations = {"classpath:application.yml"})
	static class ApplicationTestConfig {}
}
