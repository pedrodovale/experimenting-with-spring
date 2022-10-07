package com.pedrodovale.experimentingwith.spring.events.service;

import static java.util.stream.Collectors.toList;

import com.pedrodovale.experimentingwith.spring.events.event.NewBookEvent;
import com.pedrodovale.experimentingwith.spring.events.model.Book;
import com.pedrodovale.experimentingwith.spring.events.repository.BookShelf;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BooksService implements ApplicationEventPublisherAware {

  private final BookShelf bookShelf;
  @Setter private ApplicationEventPublisher applicationEventPublisher;

  public void notifyNewBook(Book book) {
    applicationEventPublisher.publishEvent(new NewBookEvent(this, book));
  }

  public List<String> getAll() {
    return bookShelf.getAllIds().stream().map(java.util.UUID::toString).collect(toList());
  }
}
