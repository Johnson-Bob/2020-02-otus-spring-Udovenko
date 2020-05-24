package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.web.AuthorModel;
import ru.otus.spring.booklibrary.service.AuthorService;

import java.util.Set;

import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toAuthorModelSet;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public Set<AuthorModel> getAllAuthors() {
        final Set<AuthorDto> allAuthors = service.getAllAuthors();
        return toAuthorModelSet(allAuthors);
    }
}
