package ru.otus.spring.studenttestingsboot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InternationalizationServiceTest {
    @Autowired
    private InternationalizationService service;

    @AfterEach
    public void afterTest() {
        service.setCurrentLocale(new Locale("en"));
    }

    @Test
    public void getEnglishMessageWhenEnglishLanguage() {
//        Arrange
        service.setCurrentLocale(new Locale("en"));

//        Assert
        assertThat("Hello world!").isEqualTo(service.getMessage("test.message"));
    }

    @Test
    public void getRussianMessageWhenRussianLanguage() {
//        Arrange
        service.setCurrentLocale(new Locale("ru"));

//        Assert
        assertThat("Привет мир!").isEqualTo(service.getMessage("test.message"));
    }

    @Test
    public void getEnglishMessageWhenNotEnglishAndNotRussionLanguage() {
//        Arrange
        service.setCurrentLocale(new Locale("pt"));

//        Assert
        assertThat("Hello world!").isEqualTo(service.getMessage("test.message"));
    }

    @SpringBootConfiguration
    static class InternationalizationServiceTestConfig {
        @Bean
        public InternationalizationService i18nService() {
            return new InternationalizationService();
        }
    }

}