package ru.otus.spring.booklibrary.bee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;

import ru.otus.spring.booklibrary.bee.changelog.DatabaseChangelog;

@Configuration
public class MongoBeeConfig {

  @Autowired
  private MongoClient mongo;

  @Bean
  public Mongobee mongobee(Environment environment) {
    Mongobee runner = new Mongobee(mongo);
    runner.setDbName("book-library");
    runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
    runner.setSpringEnvironment(environment);
    return runner;
  }
}
