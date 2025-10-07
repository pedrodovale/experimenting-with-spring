package com.pedrodovale.experimentingwith.spring.ssl.server.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.pedrodovale.experimentingwith.spring.ssl.server.model.Book;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<Book> books() {
    return getBooks();
  }

  public static List<Book> getBooks() {
    Book book = new Book();
    book.setId("1");
    book.setTitle("A Study in Scarlet");
    return Collections.singletonList(book);
  }
}
