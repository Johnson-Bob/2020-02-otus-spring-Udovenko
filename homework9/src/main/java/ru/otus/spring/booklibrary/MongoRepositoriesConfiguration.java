package ru.otus.spring.booklibrary;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring.booklibrary.dao")
public class MongoRepositoriesConfiguration {
}
