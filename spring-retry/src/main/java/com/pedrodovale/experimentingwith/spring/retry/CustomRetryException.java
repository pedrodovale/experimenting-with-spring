package com.pedrodovale.experimentingwith.spring.retry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomRetryException extends RuntimeException {

  private final int attemptCounter;
}
