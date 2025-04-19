package dev.pablo.pricechecker.application.port.in;

import java.util.List;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;

public interface PriceServicePort {

  public List<PriceEntity> findPricesBetweenDatesWithMaxPriority(PriceFilterInput priceFilterInput);

}
