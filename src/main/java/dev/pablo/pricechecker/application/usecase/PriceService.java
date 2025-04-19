package dev.pablo.pricechecker.application.usecase;

import java.util.List;
import org.springframework.stereotype.Service;
import dev.pablo.pricechecker.application.port.in.PriceServicePort;
import dev.pablo.pricechecker.application.port.out.PriceRepositoryPort;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PriceService implements PriceServicePort {

  private final PriceRepositoryPort priceRepositoryPort;

  @Override
  public List<PriceEntity> findPricesBetweenDatesWithMaxPriority(PriceFilterInput priceFilterInput) {
    return priceRepositoryPort.findPricesBetweenDatesWithMaxPriority(priceFilterInput);
  }

}
