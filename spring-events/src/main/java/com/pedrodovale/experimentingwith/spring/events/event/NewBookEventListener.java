package com.pedrodovale.experimentingwith.spring.events.event;

import com.pedrodovale.experimentingwith.spring.events.model.Book;
import com.pedrodovale.experimentingwith.spring.events.repository.BookShelf;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewBookEventListener implements ApplicationListener<NewBookEvent> {

  private final BookShelf bookShelf;

  @Override
  public void onApplicationEvent(NewBookEvent event) {
    Book book = event.getBook();
    UUID bookId = bookShelf.create(book);
    log.info("new book: {}", bookId);
  }
}
