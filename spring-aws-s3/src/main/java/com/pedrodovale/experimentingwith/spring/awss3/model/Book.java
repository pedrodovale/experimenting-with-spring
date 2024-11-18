package com.pedrodovale.experimentingwith.spring.awss3.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Book implements Serializable {
  private String id;
  private String title;
}
