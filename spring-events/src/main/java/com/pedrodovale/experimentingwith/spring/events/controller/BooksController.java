package com.pedrodovale.experimentingwith.spring.events.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;

import com.pedrodovale.experimentingwith.spring.events.service.BooksService;
import com.pedrodovale.experimentingwith.spring.events.model.Book;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

  private final BooksService booksService;

  @GetMapping
  public List<String> getAllBooks() {
    return booksService.getAll();
  }

  @PostMapping(value = "/notify-new-book")
  @ResponseStatus(ACCEPTED)
  public void notifyNewBook(@RequestBody @Valid Book book) {
    booksService.notifyNewBook(book);
  }
}
