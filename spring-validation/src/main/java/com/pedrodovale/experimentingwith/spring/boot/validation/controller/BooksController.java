package com.pedrodovale.experimentingwith.spring.boot.validation.controller;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import com.pedrodovale.experimentingwith.spring.boot.validation.exception.BookNotFoundException;
import com.pedrodovale.experimentingwith.spring.boot.validation.model.Book;
import com.pedrodovale.experimentingwith.spring.boot.validation.service.BooksService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

  private final BooksService booksService;

  @PostMapping
  @ResponseStatus(CREATED)
  public void createBook(
      HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid Book book) {
    String bookId = booksService.create(book);
    String uriString =
        UriComponentsBuilder.fromUriString(request.getRequestURL() + "/{bookId}")
            .buildAndExpand(bookId)
            .toUriString();
    response.addHeader(LOCATION, uriString);
  }

  @GetMapping("/{bookId}")
  public ResponseEntity<Book> getBook(@PathVariable String bookId) {
    Book book = booksService.get(bookId);
    if (book == null) {
      throw new BookNotFoundException(bookId);
    }
    return ok(book);
  }
}
