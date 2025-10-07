package com.pedrodovale.experimentingwith.spring.ssl.client.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Book implements Serializable {
  private String id;
  private String title;
}
