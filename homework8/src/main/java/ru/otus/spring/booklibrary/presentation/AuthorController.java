package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.service.AuthorService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final ConsoleProcessor consoleProcessor;
    private final AuthorService authorService;

    public Set<AuthorDto> inputAuthors() {
        Set<AuthorDto> result = new HashSet<>();
        String hasNextAuthor;
        do {
            String firstName = consoleProcessor.waitAnswer("Please, type author first name");
            String lastName = consoleProcessor.waitAnswer("Please, type author last name");
            AuthorDto authorDto = AuthorDto.builder().firstName(firstName).lastName(lastName).build();
            result.add(authorService.findOrSaveAuthor(authorDto));

            hasNextAuthor = consoleProcessor.waitAnswer("Click \"Enter\" for input next author or type \"stop\"");
        } while (!hasNextAuthor.equalsIgnoreCase("stop"));

        return result;
    }

    public void outputAllAuthors() {
        String allAuthors = authorService.getAllAuthors().stream()
                .map(AuthorDto::shortString)
                .collect(Collectors.joining(", "));
        consoleProcessor.outputString(allAuthors);
    }
}
