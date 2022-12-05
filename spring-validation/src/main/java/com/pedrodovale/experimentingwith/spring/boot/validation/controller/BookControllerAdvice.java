package com.pedrodovale.experimentingwith.spring.boot.validation.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.pedrodovale.experimentingwith.spring.boot.validation.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class BookControllerAdvice {

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public void handleBookNotFoundException(BookNotFoundException e) {
    log.warn("handling exception for book not found: {}", e.getBookId());
  }

}
