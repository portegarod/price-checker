package dev.pablo.pricechecker.provider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;

public class PriceFilterProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    List<PriceEntity> priceEntityList = List.of(
        PriceEntity.builder().id(1L).startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
            .endDate(LocalDateTime.parse("2020-12-31T23:59:59")).brandId("1").productId("35455")
            .priceList("1")
            .priority(0).price(BigDecimal.valueOf(35.50)).currency("EUR")
            .build());

    PriceFilterInput priceFilterInput =
        new PriceFilterInput(LocalDateTime.parse("2020-06-14T00:00:00"), "1", "35455", null
        );

    return Stream.of(Arguments.of(priceEntityList, priceFilterInput));
  }

}
