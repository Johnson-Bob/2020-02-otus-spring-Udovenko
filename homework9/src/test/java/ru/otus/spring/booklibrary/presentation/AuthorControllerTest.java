package ru.otus.spring.booklibrary.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.service.AuthorService;

@DisplayName("Test Author REST controller")
@WebMvcTest(controllers = {AuthorController.class})
class AuthorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthorService service;

  @Test
  @DisplayName("Should return list of authors")
  void testGetAllAuthors() throws Exception {
    AuthorDto authorDto = AuthorDto.builder().id("1").firstName("FName").lastName("LName").build();
    given(service.getAllAuthors()).willReturn(Set.of(authorDto));

    this.mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(authorDto.getId())))
      .andExpect(jsonPath("$[0].firstName", is(authorDto.getFirstName())))
      .andExpect(jsonPath("$[0].lastName", is(authorDto.getLastName())));
  }
}
