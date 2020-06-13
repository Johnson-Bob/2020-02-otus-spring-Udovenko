package ru.otus.spring.booklibrary.presentation;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.web.AuthorModel;
import ru.otus.spring.booklibrary.model.web.BookModel;
import ru.otus.spring.booklibrary.service.BookService;

@DisplayName("Test BookController Rest API")
@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BookService service;

  @Test
  @DisplayName("Should return status 'created' and saved book in body")
  void testAddBookToLibrary() throws Exception {
    BookModel book = getBook();
    BookDto afterSave = BookDto.builder().id("1").bookTitle("").genre("")
      .authors(Collections.emptySet()).build();
    BDDMockito.given(service.addBookToLibrary(Mockito.any())).willReturn(afterSave);

    mvc
      .perform(MockMvcRequestBuilders.post("/books").content(getJsonString(book))
        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated()).andExpect(jsonPath("$.id", is("1")));

  }

  @Test
  @DisplayName("Should return MethodArgumentNotValidException")
  public void testNotValidArgument() throws Exception {
    BookModel book = getBook();
    book.setBookTitle("");
    book.setGenre(null);
    book.setAuthors(Collections.emptySet());

    mvc
      .perform(MockMvcRequestBuilders.post("/books").content(getJsonString(book))
        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print()).andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.bookTitle", is("Title required")))
      .andExpect(jsonPath("$.genre", is("Genre required")))
      .andExpect(jsonPath("$.authors", is("The book must have at least one author")));
  }

  private BookModel getBook() {
    AuthorModel author1 = new AuthorModel();
    author1.setId("4we451");
    author1.setFirstName("Petr");
    author1.setLastName("Petrov");

    AuthorModel author2 = new AuthorModel();
    author2.setId("67vhf4");
    author2.setFirstName("Ivan");
    author2.setLastName("Ivanov");

    BookModel book = new BookModel();
    book.setBookTitle("Book title");
    book.setGenre("Detective");
    book.setAuthors(Set.of(author1, author2));

    return book;
  }

  private String getJsonString(BookModel model) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(model);
  }

}