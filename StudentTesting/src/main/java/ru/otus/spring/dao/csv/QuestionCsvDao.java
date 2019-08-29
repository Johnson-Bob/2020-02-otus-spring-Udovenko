package ru.otus.spring.dao.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.spring.dao.Dao;
import ru.otus.spring.entity.Question;
import ru.otus.spring.exception.DaoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class QuestionCsvDao implements Dao<Question> {
    private final File testFile;
    private Map<Long, Question> questionRepository = new HashMap<>();

    public QuestionCsvDao(String fileName) throws URISyntaxException {
        this.testFile = new File(getClass().getResource(fileName).getFile());
    }

    @Override
    public List<Question> getAll() throws DaoException {
        List<Question> result;
        if (questionRepository.isEmpty()) {
            result = readFromFile();
        } else {
            result = new ArrayList<>(questionRepository.values());
        }

        return result;
    }

    @Override
    public Question save(Question entity) throws DaoException {
        return entity;
    }

    @Override
    public Question getById(long id) throws DaoException {
        return questionRepository.get(id);
    }

    private void saveToRepository(List<Question> questions) {
        questionRepository = questions.stream().collect(toMap(Question::getId, identity()));
    }

    private List<Question> readFromFile() {
        List<Question> result;
        try {
            result = new CsvToBeanBuilder(new FileReader(testFile)).withType(Question.class).build().parse();
            saveToRepository(result);
        } catch (FileNotFoundException e) {
            throw new DaoException("file not found", e);
        }

        return result;
    }
}
