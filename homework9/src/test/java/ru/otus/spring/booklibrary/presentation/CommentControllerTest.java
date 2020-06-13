package ru.otus.spring.booklibrary.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.web.CommentModel;
import ru.otus.spring.booklibrary.service.CommentService;

@DisplayName("Test Comment Rest API")
@WebMvcTest(CommentController.class)
class CommentControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CommentService service;

  @Test
  @DisplayName("should return status 'ok' and Comment list when all book comments are requested")
  void testGetAllBookComments() throws Exception {
    CommentDto commentDto = CommentDto.builder().text("comment")
      .bookDto(BookDto.builder().id("1").build()).create(LocalDateTime.now()).build();
    given(service.getAllBookComments(Mockito.any())).willReturn(List.of(commentDto));

    mvc.perform(get("/books/comments/{bookId}", 1)).andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  @DisplayName("should return status 'created' when new comment to book is added")
  void testAddCommentToBook() throws Exception {
    CommentModel comment = getComment();
    willDoNothing().given(service).saveComment(any());

    mvc.perform(post("/books/comments").content(getJsonString(comment))
      .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")).andExpect(status().isCreated());
  }

  @Test
  @DisplayName("should return status 'ok' when comment is deleted")
  void testDeleteBookComment() throws Exception {
    CommentModel comment = getComment();
    willDoNothing().given(service).saveComment(any());

    mvc.perform(delete("/books/comments").content(getJsonString(comment))
      .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
      .characterEncoding("UTF-8")).andExpect(status().isOk());
  }

  private String getJsonString(CommentModel model) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.writeValueAsString(model);
  }

  private CommentModel getComment() {
    CommentModel comment = new CommentModel();
    comment.setBookId("1cdfg56");
    comment.setText("Comment text");
    comment.setCreate(LocalDateTime.now());

    return comment;
  }

}