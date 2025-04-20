package dev.pablo.pricechecker.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRICES")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "BRAND_ID")
  private String brandId;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "PRICE_LIST")
  private String priceList;

  @Column(name = "PRODUCT_ID")
  private String productId;

  @Column(name = "PRIORITY")
  private Integer priority;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CURR")
  private String currency;
}
