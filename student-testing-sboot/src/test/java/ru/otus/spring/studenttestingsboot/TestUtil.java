package ru.otus.spring.studenttestingsboot;

import ru.otus.spring.studenttestingsboot.entity.Answer;
import ru.otus.spring.studenttestingsboot.entity.Question;
import ru.otus.spring.studenttestingsboot.entity.User;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Question> createQuestionList() {
        List<Question> questionList = new ArrayList<>();

        Question firstQuestion = new Question();
        firstQuestion.setId(1);
        firstQuestion.setText("Test question 1?");
        firstQuestion.setAnswers(createAnswerList());
        firstQuestion.getAnswers().remove(1);
        firstQuestion.getAnswers().remove(3);

        Question secondQuestion = new Question();
        secondQuestion.setId(2);
        secondQuestion.setText("Test question 2?");
        secondQuestion.setAnswers(createAnswerList());
        secondQuestion.getAnswers().remove(2);
        secondQuestion.getAnswers().remove(2);

        questionList.add(firstQuestion);
        questionList.add(secondQuestion);

        return questionList;
    }

    public static List<Answer> createAnswerList() {
        List<Answer> answerList = new ArrayList<>();

        Answer answerA = Answer.builder().letter('A').answerText("The incorrect answer A").isCorrect(false).build();
        Answer correctAnswerB = Answer.builder().letter('B').answerText("The correct answer B").isCorrect(true).build();
        Answer incorrectAnswerB = Answer.builder().letter('B').answerText("The incorrect answer B").isCorrect(false).build();
        Answer correctAnswerC = Answer.builder().letter('C').answerText("The correct answer C").isCorrect(true).build();
        Answer incorrectAnswerC = Answer.builder().letter('C').answerText("The incorrect answer C").isCorrect(false).build();
        Answer answerD = Answer.builder().letter('D').answerText("The incorrect answer D").isCorrect(false).build();

        answerList.add(answerA);
        answerList.add(correctAnswerB);
        answerList.add(incorrectAnswerB);
        answerList.add(correctAnswerC);
        answerList.add(incorrectAnswerC);
        answerList.add(answerD);

        return answerList;
    }

    public static User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }
}
