package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InternationalizationServiceTest.InternationalizationServiceTestConfig.class)
class InternationalizationServiceTest {
    @Autowired
    private InternationalizationService service;

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

    @Configuration
    @PropertySource("classpath:application.properties")
    public static class InternationalizationServiceTestConfig {
        @Bean
        public InternationalizationService i18nService() {
            return new InternationalizationService();
        }

        @Bean
        public PropertySourcesPlaceholderConfigurer propertyConfigure() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

}