package ru.otus.spring.studenttestingsboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("i18nService")
public class InternationalizationService {
    @Value("#{systemProperties['user.language']}")
    private Locale currentLocale;
    private final MessageSource messageSource;

    public InternationalizationService() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object...objects) {
        return messageSource.getMessage(key, objects, currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }
}
