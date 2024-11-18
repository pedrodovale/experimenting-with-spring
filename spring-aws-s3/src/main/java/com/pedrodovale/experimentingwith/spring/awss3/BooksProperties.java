package com.pedrodovale.experimentingwith.spring.awss3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.annotations.NotNull;

import static com.pedrodovale.experimentingwith.spring.awss3.BooksProperties.PREFIX;

@ConfigurationProperties(prefix = PREFIX)
@Getter
@Setter
public class BooksProperties {

  public static final String PREFIX = "com.pedrodovale.experimentingwith.spring.books";

  @NotNull private String bucket;
}
