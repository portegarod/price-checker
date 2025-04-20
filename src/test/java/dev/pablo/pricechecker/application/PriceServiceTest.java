package dev.pablo.pricechecker.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.pablo.pricechecker.application.port.out.PriceRepositoryPort;
import dev.pablo.pricechecker.application.usecase.PriceService;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.provider.PriceFilterProvider;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

  @InjectMocks
  private PriceService priceService;

  @Mock
  private PriceRepositoryPort priceRepositoryPort;

  @ParameterizedTest
  @ArgumentsSource(PriceFilterProvider.class)
  void shouldReturnPricesWhenValidFilterIsGiven(List<PriceEntity> priceEntityListMock,
      PriceFilterInput priceFilterInputMock) {
    Mockito.when(priceRepositoryPort.findPricesBetweenDatesWithMaxPriority(Mockito.any()))
        .thenReturn(priceEntityListMock);

    List<PriceEntity> priceEntityList =
        priceService.findPricesBetweenDatesWithMaxPriority(priceFilterInputMock);


    verify(priceRepositoryPort, times(1))
        .findPricesBetweenDatesWithMaxPriority(priceFilterInputMock);
    assertEquals(1, priceEntityList.size());
  }
}
