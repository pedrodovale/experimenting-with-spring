package com.pedrodovale.experimentingwith.spring.permissionevaluator.service;

import com.pedrodovale.experimentingwith.spring.permissionevaluator.model.Book;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.repository.BookShelf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BooksService {

  private final BookShelf bookShelf;

  public String create(Book book) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.debug("user adding book: {}", username);
    book.setLibrarianName(username);
    return bookShelf.create(book);
  }

  public Book get(String bookId) {
    return bookShelf.getBook(bookId);
  }
}
