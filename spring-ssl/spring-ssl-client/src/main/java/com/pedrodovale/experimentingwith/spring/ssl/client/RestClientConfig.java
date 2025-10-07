package com.pedrodovale.experimentingwith.spring.ssl.client;

import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  public static final String SSL_BUNDLE_BOOK_SERVER = "book-server";

  @Bean
  public RestClient restClient(RestClientSsl restClientSsl) {
    return RestClient.builder()
        .baseUrl("https://localhost:8080")
        .apply(restClientSsl.fromBundle(SSL_BUNDLE_BOOK_SERVER))
        .build();
  }
}
