package dev.pablo.pricechecker.infraestructure.in.web.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import dev.pablo.pricechecker.domain.model.PriceFilterInput;
import dev.pablo.pricechecker.infraestructure.in.web.dto.PriceFilterResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PriceControllerMapper {

  List<PriceFilterResponse> priceEntityListToPriceFilterResponseList(List<PriceEntity> priceEntity);

  PriceFilterInput requestToPriceFilterInput(LocalDateTime priceDate, String brandId,
      String productId);

}
