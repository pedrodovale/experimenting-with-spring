package com.pedrodovale.experimentingwith.spring.boot.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookNotFoundException extends RuntimeException {

  @Getter private final String bookId;
}
