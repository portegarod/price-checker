package dev.pablo.pricechecker.infraestructure.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.infraestructure.out.adapter.PriceRepositoryAdapter;
import dev.pablo.pricechecker.infraestructure.out.repository.PriceRepository;
import dev.pablo.pricechecker.provider.PriceFilterProvider;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

  @InjectMocks
  private PriceRepositoryAdapter priceRepositoryAdapter;

  @Mock
  private PriceRepository priceRepository;

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @ArgumentsSource(PriceFilterProvider.class)
  void shouldReturnPricesWhenValidFilterIsGiven(List<PriceEntity> priceEntityListMock,
      PriceFilterInput priceFilterInputMock) {
    Pageable pageable = PageRequest.of(0, 10);
    Page<PriceEntity> priceEntityPage =
        new PageImpl<>(priceEntityListMock, pageable, priceEntityListMock.size());

    Mockito.when(priceRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable)))
        .thenReturn(priceEntityPage);

    List<PriceEntity> priceEntityList =
        priceRepositoryAdapter.findPricesBetweenDatesWithMaxPriority(priceFilterInputMock);

    verify(priceRepository, times(1)).findAll(Mockito.any(Specification.class),
        Mockito.eq(pageable));
    assertEquals(1, priceEntityList.size());
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @ArgumentsSource(PriceFilterProvider.class)
  void shouldThrowExceptionWhenNoPricesFound(List<PriceEntity> priceEntityListMock,
      PriceFilterInput priceFilterInputMock) {
    Pageable pageable = PageRequest.of(0, 10);
    Page<PriceEntity> priceEntityPage = new PageImpl<>(Collections.EMPTY_LIST, pageable, 0);

    Mockito.when(priceRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable)))
        .thenReturn(priceEntityPage);

    assertThrows(NoSuchElementException.class, () -> {
        priceRepositoryAdapter.findPricesBetweenDatesWithMaxPriority(priceFilterInputMock);
    });

    verify(priceRepository, times(1)).findAll(Mockito.any(Specification.class),
        Mockito.eq(pageable));
  }

}
