package com.pedrodovale.experimentingwith.spring.ssl.client;

import com.pedrodovale.experimentingwith.spring.ssl.client.model.Book;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringSslClientApplication {

  private final RestClient restClient;

  public static void main(String[] args) {
    SpringApplication.run(SpringSslClientApplication.class, args);
  }

  @Bean
  public CommandLineRunner run() throws Exception {
    return args -> {
      Book[] books = restClient.get().uri("/books").retrieve().body(Book[].class);
      System.out.println("books: " + Arrays.deepToString(books));
    };
  }
}
