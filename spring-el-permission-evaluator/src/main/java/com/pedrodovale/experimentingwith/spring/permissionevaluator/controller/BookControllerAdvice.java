package com.pedrodovale.experimentingwith.spring.permissionevaluator.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookClubSessionNotStartedException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookNotFoundException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.BookReaderNotFoundException;
import com.pedrodovale.experimentingwith.spring.permissionevaluator.exception.NotABookClubMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class BookControllerAdvice {

  private final ObjectMapper objectMapper;

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public void handleBookNotFoundException(BookNotFoundException e) {
    log.warn("handling exception for book not found: {}", e.getBookId());
  }

  @ExceptionHandler(BookReaderNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public void handleBookReaderNotFoundException(BookReaderNotFoundException e) {
    log.warn("handling exception for book reader not found in users: {}", e.getReaderUsername());
  }

  @ExceptionHandler(BookClubSessionNotStartedException.class)
  @ResponseStatus(BAD_REQUEST)
  public JsonNode handleBookClubSessionNotStartedException(BookClubSessionNotStartedException e) {
    log.warn("handling exception for book club session not started: {}", e.getBookId());
    return objectMapper
        .createObjectNode()
        .put("error", "session_not_starter")
        .put(
            "error_description",
            "The session for book "
                + e.getBookId()
                + " has not starter. Ask the book club master to start the session.");
  }

  @ExceptionHandler(NotABookClubMemberException.class)
  @ResponseStatus(BAD_REQUEST)
  public JsonNode handleNotABookClubMemberException(NotABookClubMemberException e) {
    log.warn(
        "handling exception for reader not member of the book club: {}", e.getReaderUsername());
    return objectMapper
        .createObjectNode()
        .put("error", "not_book_club_member")
        .put(
            "error_description",
            "The reader " + e.getReaderUsername() + " is not a member of the book club for book.");
  }
}
