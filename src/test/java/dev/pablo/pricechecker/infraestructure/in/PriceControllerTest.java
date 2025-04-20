package dev.pablo.pricechecker.infraestructure.in;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import dev.pablo.pricechecker.application.port.in.PriceServicePort;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.infraestructure.in.web.controller.PriceController;
import dev.pablo.pricechecker.infraestructure.in.web.dto.PriceFilterResponse;
import dev.pablo.pricechecker.infraestructure.in.web.mapper.CommonControllerMapper;
import dev.pablo.pricechecker.infraestructure.in.web.mapper.PriceControllerMapper;
import dev.pablo.pricechecker.provider.PriceFilterProvider;


@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

  @InjectMocks
  private PriceController priceController;

  @Mock
  private PriceServicePort priceServicePort;

  @Mock
  private CommonControllerMapper commonControllerMapper;

  private PriceControllerMapper priceControllerMapper;


  @BeforeEach
  void setUp() {
    priceControllerMapper = Mappers.getMapper(PriceControllerMapper.class);
    ReflectionTestUtils.setField(priceController, "priceControllerMapper", priceControllerMapper);
    ReflectionTestUtils.setField(priceControllerMapper, "commonControllerMapper",
        commonControllerMapper);
  }

  @ParameterizedTest
  @ArgumentsSource(PriceFilterProvider.class)
  void shouldReturnPricesWhenValidFilterIsGiven(List<PriceEntity> priceEntityListMock,
      PriceFilterInput priceFilterInputMock) {
    Mockito.when(priceServicePort.findPricesBetweenDatesWithMaxPriority(Mockito.any()))
        .thenReturn(priceEntityListMock);

    List<PriceFilterResponse> priceFilterResponse = priceController.priceFilter(
        LocalDateTime.parse("2020-06-14T00:00:00"), "1", "35455", PageRequest.of(0, 10));

    assertAll(() -> assertEquals(1, priceFilterResponse.size()),
        () -> assertEquals("1", priceFilterResponse.get(0).getBrandId()),
        () -> assertEquals("35455", priceFilterResponse.get(0).getProductId()),
        () -> assertEquals("1", priceFilterResponse.get(0).getPriceList()),
        () -> assertEquals(LocalDateTime.parse("2020-06-14T00:00:00"),
            priceFilterResponse.get(0).getStartDate()),
        () -> assertEquals(LocalDateTime.parse("2020-12-31T23:59:59"),
            priceFilterResponse.get(0).getEndDate()),
        () -> assertEquals(BigDecimal.valueOf(35.50), priceFilterResponse.get(0).getPrice())
    );
  }

}
