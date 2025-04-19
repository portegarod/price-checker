package dev.pablo.pricechecker.application.port.out;

import java.util.List;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;

public interface PriceRepositoryPort {

  public List<PriceEntity> findPricesBetweenDatesWithMaxPriority(PriceFilterInput data);

}
