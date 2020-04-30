package ru.otus.spring.booklibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.spring.booklibrary.dao.AuthorDao;
import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@DisplayName("AuthorService work with authors")
@SpringBootTest
class AuthorServiceImplTest {
    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorDao dao;

    private AuthorDto testDto = AuthorDto.builder().firstName("TestFName").lastName("TestLName").build();

    @Test
    @DisplayName("should don't save new author if found in database")
    void shouldNotSave() {
        Author mockAuthor = new Author(1L, testDto.getFirstName(), testDto.getLastName());
        Mockito.when(dao.findByFirstAndLastNames(anyString(), anyString())).thenReturn(Optional.of(mockAuthor));

        service.findOrSaveAuthor(testDto);
        Mockito.verify(dao, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("should save new author if didn't find in database")
    void shouldSave() {
        Author mockAuthor = new Author(1L, testDto.getFirstName(), testDto.getLastName());
        Mockito.when(dao.findByFirstAndLastNames(anyString(), anyString())).thenReturn(Optional.empty());
        Mockito.when(dao.save(any())).thenReturn(mockAuthor);

        service.findOrSaveAuthor(testDto);
        Mockito.verify(dao, Mockito.times(1)).save(any());
    }

    @SpringBootConfiguration
    static class AuthorServiceImplTestConfiguration {
        @Bean
        AuthorService service(AuthorDao dao) {
            return new AuthorServiceImpl(dao);
        }
    }

}