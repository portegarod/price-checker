package dev.pablo.pricechecker.domain.persistence;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import dev.pablo.pricechecker.domain.model.PriceEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class PriceSpecification {

  private static final String BRAND_ID_COLUMN = "brandId";
  private static final String PRODUCT_ID_COLUMN = "productId";
  private static final String PRIORITY_COLUMN = "priority";
  private static final String START_DATE_COLUMN = "startDate";
  private static final String END_DATE_COLUMN = "endDate";

  private PriceSpecification() {}


  public static Specification<PriceEntity> withProductId(String productId) {
    return (Root<PriceEntity> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) -> equalPredicate(criteriaBuilder, root, PRODUCT_ID_COLUMN,
            productId);
  }

  public static Specification<PriceEntity> withBrandId(String brandId) {
    return (Root<PriceEntity> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) -> equalPredicate(criteriaBuilder, root, BRAND_ID_COLUMN,
            brandId);
  }

  public static Specification<PriceEntity> dateBetween(LocalDateTime date) {
    return (Root<PriceEntity> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) -> date == null ? null
            : dateBetween(criteriaBuilder, root, date);
  }

  public static Specification<PriceEntity> withMaxPriority(LocalDateTime date) {
    return (Root<PriceEntity> root, CriteriaQuery<?> criteriaQuery,
        CriteriaBuilder criteriaBuilder) -> {
      if (date == null) {
        return null;
      }

      Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
      Root<PriceEntity> subRoot = subquery.from(PriceEntity.class);

      Predicate equalBrandId = equalBetweenRoots(criteriaBuilder, root, subRoot, BRAND_ID_COLUMN);
      Predicate equalProductId =
          equalBetweenRoots(criteriaBuilder, root, subRoot, PRODUCT_ID_COLUMN);
      Predicate dateBetween = dateBetween(criteriaBuilder, subRoot, date);

      subquery.select(criteriaBuilder.max(subRoot.get(PRIORITY_COLUMN)));
      subquery.where(equalBrandId, equalProductId, dateBetween);

      return criteriaBuilder.equal(root.get(PRIORITY_COLUMN), subquery);
    };
  }

  private static <T> Predicate equalPredicate(CriteriaBuilder criteriaBuilder,
      Root<PriceEntity> root, String column, T field) {
    return field == null ? null : criteriaBuilder.equal(root.get(column), field);
  }

  private static <T> Predicate equalBetweenRoots(CriteriaBuilder cb, Root<T> root, Root<T> subRoot,
      String field) {
    return cb.equal(root.get(field), subRoot.get(field));
  }

  private static Predicate dateBetween(CriteriaBuilder criteriaBuilder, Root<PriceEntity> root,
      LocalDateTime date) {
    return criteriaBuilder.between(criteriaBuilder.literal(date), root.get(START_DATE_COLUMN),
        root.get(END_DATE_COLUMN));
  }

}
