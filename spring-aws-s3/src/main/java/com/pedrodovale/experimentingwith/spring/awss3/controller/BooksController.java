package com.pedrodovale.experimentingwith.spring.awss3.controller;

import com.pedrodovale.experimentingwith.spring.awss3.model.Book;
import com.pedrodovale.experimentingwith.spring.awss3.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
public class BooksController {

  private final BooksService booksService;

  @PostMapping(
      value = "/books/import",
      consumes = MULTIPART_FORM_DATA_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public List<Book> importBooks(@RequestPart(name = "books") MultipartFile multipartFile) {
    return booksService.processAndBackup(multipartFile);
  }
}
