package ru.otus.spring.studenttestingsboot.dao.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.studenttestingsboot.annotation.UpdateCache;
import ru.otus.spring.studenttestingsboot.dao.Dao;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.exception.DaoException;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Repository
public class QuestionCsvDao implements Dao<Question> {
    @Value("classpath:#{i18nService.getMessage('questions.file')}")
    private Resource questionsResource;
    private Map<Long, Question> questionRepository = new HashMap<>();

    @PostConstruct
    public void init(){
        updateCache();
    };

    @Override
    public List<Question> getAll() throws DaoException {
        return new ArrayList<>(questionRepository.values());
    }

    @Override
    public Question save(Question entity) throws DaoException {
        return entity;
    }

    @Override
    public Question getById(long id) throws DaoException {
        Question result = questionRepository.get(id);

        if (result == null) {
            throw new DaoException(String.format("Question with ID=%d not found", id));
        }

        return result;
    }

    private void saveToRepository(List<Question> questions) {
        questionRepository = questions.stream().collect(toMap(Question::getId, identity()));
    }

    @UpdateCache
    @Override
    public List<Question> updateCache() {
        List<Question> result;
        try {
            result = new CsvToBeanBuilder(new FileReader(questionsResource.getFile())).withType(Question.class).build().parse();
            saveToRepository(result);
        } catch (FileNotFoundException e) {
            throw new DaoException("file not found", e);
        } catch (IOException e) {
            throw new DaoException("Error when reading file", e);
        }

        return result;
    }
}
