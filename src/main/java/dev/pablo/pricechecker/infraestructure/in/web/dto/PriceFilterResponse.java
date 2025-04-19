package dev.pablo.pricechecker.infraestructure.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PriceFilterResponse {

  @JsonProperty("product_id")
  private String productId;

  @JsonProperty("brand_id")
  private String brandId;

  @JsonProperty("price_list")
  private String priceList;

  @JsonProperty("start_date")
  private LocalDateTime startDate;

  @JsonProperty("end_date")
  private LocalDateTime endDate;

  private BigDecimal price;
}
