package ru.otus.spring.entity;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.dao.csv.TextToAnswer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Question {
    @CsvBindByName
    private long id;
    @CsvBindByName
    private String text;
    @CsvBindAndSplitByName(elementType = Answer.class, splitOn = ";+", writeDelimiter = ";",
            collectionType = ArrayList.class, converter = TextToAnswer.class)
    private List<Answer> answers = new ArrayList<>();

    @Override
    public String toString() {
        return id + ". " + text;
    }
}
