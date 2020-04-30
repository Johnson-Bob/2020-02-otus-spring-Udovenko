package ru.otus.spring.booklibrary.presentation;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConsoleProcessor {
    private final Scanner scanner = new Scanner(System.in);

    public <T> T waitAndCheckAnswer(Map<String, T> answerVariants, String invite) {
        displayMapOnScreen(answerVariants);
        String userChoice;
        do {
            String selectedNumb = waitAnswer(invite, Pattern.compile("^\\d+"));
            userChoice = answerVariants.containsKey(selectedNumb) ? selectedNumb : null;
        } while (userChoice == null);

        return answerVariants.get(userChoice);
    }

    public String waitAnswer(String invite) {
        System.out.println(invite);
        return scanner.nextLine();
    }

    public String waitAnswer(String invite, Pattern pattern) {
        String result;
        do {
            System.out.println(invite);
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            result = matcher.find() ? matcher.group(0) : null;
        } while (result == null);

        return result;
    }

    public void outputString(String string) {
        System.out.println(string);
    }

    public  <K, V> void displayMapOnScreen(Map<K, V> map) {
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }
}
