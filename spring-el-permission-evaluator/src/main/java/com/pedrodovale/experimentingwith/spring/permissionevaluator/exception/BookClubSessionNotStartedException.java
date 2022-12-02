package com.pedrodovale.experimentingwith.spring.permissionevaluator.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookClubSessionNotStartedException extends RuntimeException {

  @Getter private final String bookId;

}
