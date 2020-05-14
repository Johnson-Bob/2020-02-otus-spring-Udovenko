package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.web.AuthorModel;
import ru.otus.spring.booklibrary.service.AuthorService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public List<AuthorModel> getAllAuthors() {
        Set<AuthorDto> allAuthors = service.getAllAuthors();
        return allAuthors.stream()
                .map(this::toAuthorModel)
                .collect(Collectors.toList());
    }

    private AuthorModel toAuthorModel(AuthorDto dto) {

        if (dto == null) {
            return null;
        }
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(dto.getId());
        authorModel.setFirstName(dto.getFirstName());
        authorModel.setLastName(dto.getLastName());
        return authorModel;
    }
}
