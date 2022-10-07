package com.pedrodovale.experimentingwith.spring.events.repository;

import static java.util.Collections.synchronizedMap;
import static java.util.UUID.randomUUID;

import com.pedrodovale.experimentingwith.spring.events.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class BookShelf {

  private static final Map<UUID, Book> BOOKS = synchronizedMap(new HashMap<>());

  public UUID create(Book book) {
    UUID bookId = randomUUID();
    BOOKS.put(bookId, book);
    return bookId;
  }

  public List<UUID> getAllIds() {
    return new ArrayList<>(BOOKS.keySet());
  }
}
