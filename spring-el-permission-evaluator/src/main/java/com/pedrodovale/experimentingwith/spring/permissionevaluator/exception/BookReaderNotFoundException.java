package com.pedrodovale.experimentingwith.spring.permissionevaluator.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookReaderNotFoundException extends RuntimeException {

  @Getter private final String readerUsername;

  public BookReaderNotFoundException(String readerUsername, Throwable cause) {
    super(cause);
    this.readerUsername = readerUsername;
  }
}
