package com.pedrodovale.experimentingwith.spring.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryService {

  private int attemptCounter = 0;

  @Retryable
  public int alwaysFail() {
    log.info("attempt counter: {}", ++attemptCounter);
    throw new CustomRetryException(attemptCounter);
  }

  @Recover
  public int recover(Throwable throwable) {
    if (throwable instanceof CustomRetryException) {
      log.info("No luck. Exception type: {}", throwable.getClass().getSimpleName());
      return ((CustomRetryException) throwable).getAttemptCounter();
    }
    throw new IllegalStateException();
  }
}
