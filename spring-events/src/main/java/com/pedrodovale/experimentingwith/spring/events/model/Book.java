package com.pedrodovale.experimentingwith.spring.events.model;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

  @NotBlank private String title;
}
