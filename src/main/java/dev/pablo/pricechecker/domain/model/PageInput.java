package dev.pablo.pricechecker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInput {

  private Integer page;

  private Integer size;

}
