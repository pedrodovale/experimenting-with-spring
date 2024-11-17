package com.pedrodovale.experimentingwith.spring.awss3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BooksProperties.class)
public class AwsS3Application {
  public static void main(String[] args) {
    SpringApplication.run(AwsS3Application.class, args);
  }
}
