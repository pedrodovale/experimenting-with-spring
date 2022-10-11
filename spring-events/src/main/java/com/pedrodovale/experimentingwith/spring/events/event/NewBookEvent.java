package com.pedrodovale.experimentingwith.spring.events.event;

import com.pedrodovale.experimentingwith.spring.events.model.Book;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewBookEvent extends ApplicationEvent {

  private final Book book;

  public NewBookEvent(Object source, Book book) {
    super(source);
    this.book = book;
  }
}
