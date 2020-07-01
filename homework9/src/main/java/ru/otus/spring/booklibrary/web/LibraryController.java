package ru.otus.spring.booklibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

  @GetMapping(name = "/")
  public String homePage() {
    return "index";
  }
}
