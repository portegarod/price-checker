package dev.pablo.pricechecker.infraestructure.out.adapter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dev.pablo.pricechecker.application.port.out.PriceRepositoryPort;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.domain.persistence.PriceSpecification;
import dev.pablo.pricechecker.infraestructure.out.repository.PriceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final PriceRepository priceRepository;

  @Transactional(readOnly = true)
  @Override
  public List<PriceEntity> findPricesBetweenDatesWithMaxPriority(PriceFilterInput inputData) {
    Specification<PriceEntity> priceSpecification =
        Specification.where(PriceSpecification.dateBetween(inputData.getPriceDate()))
            .and(PriceSpecification.withBrandId(inputData.getBrandId()))
            .and(PriceSpecification.withProductId(inputData.getProductId()))
            .and(PriceSpecification.withMaxPriority(inputData.getPriceDate()));

    return Optional.ofNullable(priceRepository.findAll(priceSpecification))
        .filter(list -> !list.isEmpty()).orElseThrow(NoSuchElementException::new);
  }

}
