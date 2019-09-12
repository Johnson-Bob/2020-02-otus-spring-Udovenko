package ru.otus.spring.dao.csv;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import ru.otus.spring.entity.Answer;

public class TextToAnswer extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] split = s.split("\\|+");
        return Answer.builder().letter(split[0].charAt(0)).answerText(split[1]).isCorrect("1".equals(split[2])).build();
    }
}
