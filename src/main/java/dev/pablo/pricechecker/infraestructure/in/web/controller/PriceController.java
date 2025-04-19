package dev.pablo.pricechecker.infraestructure.in.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.pablo.pricechecker.application.port.in.PriceServicePort;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.infraestructure.in.web.dto.PriceFilterResponse;
import dev.pablo.pricechecker.infraestructure.in.web.mapper.PriceControllerMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

  private final PriceServicePort priceServicePort;

  private final PriceControllerMapper priceControllerMapper;

  @GetMapping
  public List<PriceFilterResponse> priceFilter(
      @RequestParam(required = false,
          defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(
              iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
      @RequestParam(required = false) String brandId,
      @RequestParam(required = false) String productId) {
    PriceFilterInput priceFilterInput =
        priceControllerMapper.requestToPriceFilterInput(date, brandId, productId);
    List<PriceEntity> prices =
        priceServicePort.findPricesBetweenDatesWithMaxPriority(priceFilterInput);
    return priceControllerMapper.priceEntityListToPriceFilterResponseList(prices);
  }

}
