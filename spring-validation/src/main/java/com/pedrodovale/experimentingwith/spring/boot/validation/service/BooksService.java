package com.pedrodovale.experimentingwith.spring.boot.validation.service;

import com.pedrodovale.experimentingwith.spring.boot.validation.model.Book;
import com.pedrodovale.experimentingwith.spring.boot.validation.repository.BookShelf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BooksService {

  private final BookShelf bookShelf;

  public String create(Book book) {
    return bookShelf.create(book);
  }

  public Book get(String bookId) {
    return bookShelf.getBook(bookId);
  }
}
