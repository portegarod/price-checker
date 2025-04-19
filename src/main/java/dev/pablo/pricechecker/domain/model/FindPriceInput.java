package dev.pablo.pricechecker.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPriceInput {

  private LocalDateTime priceDate;

  private String brandId;

  private String productId;

}
