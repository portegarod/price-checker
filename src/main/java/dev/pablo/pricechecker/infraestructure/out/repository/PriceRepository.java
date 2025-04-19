package dev.pablo.pricechecker.infraestructure.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import dev.pablo.pricechecker.domain.model.PriceEntity;

public interface PriceRepository
    extends JpaRepository<PriceEntity, Long>, JpaSpecificationExecutor<PriceEntity> {

}
