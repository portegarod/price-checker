package dev.pablo.pricechecker.infraestructure.in.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Pageable;
import dev.pablo.pricechecker.domain.model.PageInput;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CommonControllerMapper {

  @Mapping(target = "page", source = "pageable.pageNumber")
  @Mapping(target = "size", source = "pageable.pageSize")
  PageInput pageableToPageInput(Pageable pageable);

}
