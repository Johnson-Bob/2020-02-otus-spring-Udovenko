package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.web.AuthorModel;
import ru.otus.spring.booklibrary.service.AuthorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public List<AuthorModel> getAllAuthors() {
        Set<AuthorDto> allAuthors = service.getAllAuthors();
        return toAuthorModelList(allAuthors);
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

    private List<AuthorModel> toAuthorModelList(Collection<AuthorDto> authorDtoList) {
        if (authorDtoList == null) {
            return Collections.emptyList();
        }
        List<AuthorModel> authorModelList = new ArrayList<>();
        for (AuthorDto authorDto : authorDtoList) {
            authorModelList.add(toAuthorModel(authorDto));
        }
        return authorModelList;
    }
}
