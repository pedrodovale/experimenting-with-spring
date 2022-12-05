package com.pedrodovale.experimentingwith.spring.boot.validation.repository;

import static java.util.Collections.synchronizedMap;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

import com.pedrodovale.experimentingwith.spring.boot.validation.model.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BookShelf {

  private static final Map<UUID, Book> BOOKS = synchronizedMap(new HashMap<>());

  public String create(Book book) {
    UUID bookId = randomUUID();
    BOOKS.put(bookId, book);
    return bookId.toString();
  }

  public Book getBook(String bookId) {
    return BOOKS.get(fromString(bookId));
  }
}
