package dev.pablo.pricechecker.application.port.out;

import java.util.List;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.domain.model.PriceEntity;

public interface PriceRepositoryPort {

  public List<PriceEntity> findPricesBetweenDatesWithMaxPriority(PriceFilterInput date);

}
