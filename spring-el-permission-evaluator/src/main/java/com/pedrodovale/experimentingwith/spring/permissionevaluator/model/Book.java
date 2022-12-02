package com.pedrodovale.experimentingwith.spring.permissionevaluator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

  @NotBlank private String title;
  @JsonIgnore private String librarianName;
}
