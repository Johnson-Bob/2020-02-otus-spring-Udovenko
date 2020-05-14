package ru.otus.spring.booklibrary;

import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring.booklibrary.dao")
public class MongoRepositoriesConfiguration {
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory,
                TransactionOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED).build());
    }
}
