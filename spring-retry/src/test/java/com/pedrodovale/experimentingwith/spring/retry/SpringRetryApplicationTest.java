package com.pedrodovale.experimentingwith.spring.retry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringRetryApplicationTest {

  @Autowired private RetryService retryService;

  @Test
  void when_service_then_return() {
    int attemptCounter = retryService.alwaysFail();
    assertThat(attemptCounter).isEqualTo(3);
  }
}